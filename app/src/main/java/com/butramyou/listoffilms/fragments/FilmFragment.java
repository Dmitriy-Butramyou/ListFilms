package com.butramyou.listoffilms.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.butramyou.listoffilms.R;
import com.butramyou.listoffilms.helpers.DatabaseHelper;
import com.butramyou.listoffilms.model.Film;

public class FilmFragment extends Fragment {

    private EditText filmName;
    private Film film;
    private final boolean isToView;

    public FilmFragment() {
        this.isToView = true;
    }

    public FilmFragment(Film film, boolean isToView) {
        this.film = film;
        this.isToView = isToView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_film, container, false);
        Button cancelButton = view.findViewById(R.id.update_film_cancel);
        Button updateFilmButton = view.findViewById(R.id.update_film_button);
        filmName = view.findViewById(R.id.add_film_name);

        if (film != null) {
            updateFilmButton.setText(R.string.update_film);
            filmName.setText(film.getName());
        }

        updateFilmButton.setOnClickListener(v -> {
            String name = filmName.getText().toString().trim();
            DatabaseHelper db = new DatabaseHelper(view.getContext());
            if(!name.isEmpty() && film == null) {
                Film newFilm = new Film(name, false, false);
                db.addFilm(newFilm);
                showToViewPage();
            } else if (!name.isEmpty()) {
                film.setName(name);
                db.updateFilm(film);
                showToViewPage();
            }
        });

        cancelButton.setOnClickListener(viewBtn -> {
            showToViewPage();
        });

        return view;
    }

    private void showToViewPage() {
        Fragment fragment = isToView ? new ToViewFilmsFragment() : new ViewedFilmsFragment();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }
}
