package com.butramyou.listoffilms;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.butramyou.listoffilms.databinding.ActivityMainBinding;
import com.butramyou.listoffilms.fragments.AuthorFragment;
import com.butramyou.listoffilms.fragments.FilmFragment;
import com.butramyou.listoffilms.fragments.ToViewFilmsFragment;
import com.butramyou.listoffilms.fragments.ViewedFilmsFragment;
import com.butramyou.listoffilms.helpers.BottomNavigationViewHelper;
import com.butramyou.listoffilms.service.ExportService;
import com.butramyou.listoffilms.service.ImportService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class MainActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> someActivityResultLauncher;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Uri uri = data.getData();
                        ImportService importService = new ImportService(this);
                        importService.importFile(uri);
                    }
                });

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            Fragment selectedFragment = null;

            switch (item.getItemId()) {
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
            FilmFragment filmFragment = new FilmFragment();
            transaction.replace(R.id.main_frame, filmFragment);
            transaction.commit();
        });
    }

    private void setDefaultFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_frame, ToViewFilmsFragment.getInstance());
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_import) {
            askPermissionAndBrowseFile();
            openSomeActivityForResult();
        }

        if (id == R.id.action_export) {
            new MaterialAlertDialogBuilder(this)
                    .setMessage(this.getString(R.string.is_export_file))
                    .setPositiveButton(this.getString(R.string.ok), (dialog, i) -> {
                        ExportService exportService = new ExportService(this);
                        exportService.exportFilms();
                    })
                    .setNegativeButton(this.getString(R.string.cancel), (dialog, i) -> dialog.cancel())
                    .show();
            return true;
        }

        if (id == R.id.action_about_author) {
            Fragment selectedFragment = AuthorFragment.getInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_frame, selectedFragment);
            transaction.commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void askPermissionAndBrowseFile() {
        int MY_REQUEST_CODE_PERMISSION = 1000;
        int permission = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            this.requestPermissions(
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_REQUEST_CODE_PERMISSION
            );
        }
    }

    private void openSomeActivityForResult() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/json");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        someActivityResultLauncher.launch(intent);
    }
}