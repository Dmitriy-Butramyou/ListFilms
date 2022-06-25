package com.butramyou.listoffilms;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.butramyou.listoffilms.databinding.ActivityMainBinding;
import com.butramyou.listoffilms.fragments.ToViewFilmsFragment;
import com.butramyou.listoffilms.fragments.ViewedFilmsFragment;
import com.butramyou.listoffilms.helpers.BottomNavigationViewHelper;
import com.butramyou.listoffilms.helpers.DatabaseHelper;
import com.butramyou.listoffilms.model.Film;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bottomNavigationView = findViewById(R.id.bottom_navigation);
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

        binding.fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }

    private void setDefaultFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_frame, ToViewFilmsFragment.getInstance());
        transaction.commit();
    }
}