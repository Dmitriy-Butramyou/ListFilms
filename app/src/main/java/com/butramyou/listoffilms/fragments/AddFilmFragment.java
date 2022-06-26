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

import com.butramyou.listoffilms.MainActivity;
import com.butramyou.listoffilms.R;
import com.butramyou.listoffilms.helpers.DatabaseHelper;
import com.butramyou.listoffilms.model.Film;

public class AddFilmFragment extends Fragment {

    private EditText filmName;

    public static AddFilmFragment getInstance()    {
        return new AddFilmFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_film, container, false);

        Button cancelButton = view.findViewById(R.id.add_film_cancel);
        Button addFilmButton = view.findViewById(R.id.add_film_button);
        filmName = view.findViewById(R.id.add_film_name);

        addFilmButton.setOnClickListener(v -> {
            String name = filmName.getText().toString();
            if(!name.isEmpty()) {
                DatabaseHelper db = new DatabaseHelper(view.getContext());
                Film newFilm = new Film(name, false);
                db.addFilm(newFilm);

                MainActivity.cleanToViewCash();
                showToViewPage();
            }
        });

        cancelButton.setOnClickListener(viewBtn -> {
            showToViewPage();
        });

        return view;
    }

    private void showToViewPage() {
        Fragment toViewFragment = new ToViewFilmsFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, toViewFragment);
        fragmentTransaction.commit();
    }
}
