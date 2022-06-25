package com.butramyou.listoffilms.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.butramyou.listoffilms.R;
import com.butramyou.listoffilms.helpers.DatabaseHelper;
import com.butramyou.listoffilms.model.Film;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ToViewFilmsFragment extends Fragment {

    private List<Film> toViewFilmsCash = new ArrayList<>();

    public static ToViewFilmsFragment getInstance()    {
        return new ToViewFilmsFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_to_view, container, false);

        if(toViewFilmsCash.isEmpty()) {
            DatabaseHelper db = new DatabaseHelper(view.getContext());
            toViewFilmsCash = db.getFilms(false);
        }

        List<String> filmsLabel = new ArrayList<>();
        for (Film film : toViewFilmsCash) {
            String filmName = film.getName() + " | isViewed: " + film.isViewed();
            filmsLabel.add(filmName);
        }

        ListView listView = view.findViewById(R.id.to_view_films_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                filmsLabel.toArray(new String[0])
        );
        listView.setAdapter(adapter);

        return view;
    }

    public void cleanCash() {
        toViewFilmsCash = Collections.emptyList();
    }

}
