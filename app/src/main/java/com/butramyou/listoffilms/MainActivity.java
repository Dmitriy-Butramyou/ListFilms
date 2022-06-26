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
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

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
}