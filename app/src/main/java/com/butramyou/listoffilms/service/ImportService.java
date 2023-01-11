package com.butramyou.listoffilms.service;

import android.content.Context;
import android.net.Uri;

import com.butramyou.listoffilms.helpers.DatabaseHelper;
import com.butramyou.listoffilms.model.Film;
import com.butramyou.listoffilms.utils.FileUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ImportService {

    private final Context context;
    private final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

    public ImportService(Context context) {
        this.context = context;
    }

    public void importFile(Uri uri) {
        try {
            String test = FileUtils.copyFileToInternalStorage(context, uri, "import");
            File importFile = new File(test);
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(importFile)));

            StringBuilder importJson = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                importJson.append(line);
            }
            DatabaseHelper databaseHelper = new DatabaseHelper(context);

            List<Film> films = convertToFilm(importJson.toString());
            films.forEach(film -> {
                Film newFilm = new Film(film.getName(), film.isViewed(), film.isDownloaded());
                databaseHelper.addFilm(newFilm);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Film> convertToFilm(String importJson) {
        Type listOfFilmsObject = new TypeToken<ArrayList<Film>>() {}.getType();
        return gson.fromJson(importJson, listOfFilmsObject);
    }
}