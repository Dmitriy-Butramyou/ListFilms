package com.butramyou.listoffilms.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import com.butramyou.listoffilms.helpers.DatabaseHelper;
import com.butramyou.listoffilms.model.Film;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ExportService {

    private final Context context;
    private final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

    public ExportService(Context context) {
        this.context = context;
    }

    public void exportFilms() {
        String exportedFile = createExportString();

        if (!Objects.equals(exportedFile, "")) {
            File dir = getApplicationDirectory();
            String fileName = createFileName();
            File myExternalFile = new File(dir, fileName);
            FileOutputStream fos;
            try {
                fos = new FileOutputStream(myExternalFile);
                fos.write(exportedFile.getBytes(StandardCharsets.UTF_8));
                fos.close();
                Toast.makeText(context, "Export completed successfully. " + fileName, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(context, "Export failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    private String createExportString() {
        DatabaseHelper db = new DatabaseHelper(context);
        List<Film> films = db.getAllFilms();
        return convertToJson(films);
    }

    private File getApplicationDirectory() {
        File file = new File(context.getExternalFilesDir(null), "/export");
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }

    private String convertToJson(List<Film> films) {
        return gson.toJson(films);
    }

    @SuppressLint("SimpleDateFormat")
    private String createFileName() {
        String pattern = "MM_dd_yyyy_HH-mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return String.format("export_films_%s.json", simpleDateFormat.format(new Date()));
    }
}