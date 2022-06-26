package com.butramyou.listoffilms;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.butramyou.listoffilms.databinding.ActivityMainBinding;
import com.butramyou.listoffilms.fragments.AddFilmFragment;
import com.butramyou.listoffilms.fragments.ToViewFilmsFragment;
import com.butramyou.listoffilms.fragments.ViewedFilmsFragment;
import com.butramyou.listoffilms.helpers.BottomNavigationViewHelper;
import com.butramyou.listoffilms.model.Film;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static List<Film> toViewFilmsCash = new ArrayList<>();
    private static List<Film> viewedFilmsCash = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.butramyou.listoffilms.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);


        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            Fragment selectedFragment = null;

            switch (item.getItemId())   {
                case R.id.to_view:
                    item.setChecked(true);
                    selectedFragment = ToViewFilmsFragment.getInstance();
                    break;

                case R.id.viewed:
                    item.setChecked(true);
                    selectedFragment = ViewedFilmsFragment.getInstance();
                    break;
            }

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_frame, selectedFragment);
            transaction.commit();
            return false;
        });
        setDefaultFragment();

        binding.fab.setOnClickListener(view -> {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_frame, AddFilmFragment.getInstance());
            transaction.commit();
        });
    }

    private void setDefaultFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_frame, ToViewFilmsFragment.getInstance());
        transaction.commit();
    }

    public static List<Film> getToViewFilmsCash() {
        return toViewFilmsCash;
    }

    public static void setToViewFilmsCash(List<Film> toViewFilmsCash) {
        MainActivity.toViewFilmsCash = toViewFilmsCash;
    }

    public static List<Film> getViewedFilmsCash() {
        return viewedFilmsCash;
    }

    public static void setViewedFilmsCash(List<Film> viewedFilmsCash) {
        MainActivity.viewedFilmsCash = viewedFilmsCash;
    }

    public static void cleanAllCash() {
        viewedFilmsCash = Collections.emptyList();
        toViewFilmsCash = Collections.emptyList();
    }

    public static void cleanViewedCash() {
        viewedFilmsCash = Collections.emptyList();
    }

    public static void cleanToViewCash() {
        toViewFilmsCash = Collections.emptyList();
    }
}