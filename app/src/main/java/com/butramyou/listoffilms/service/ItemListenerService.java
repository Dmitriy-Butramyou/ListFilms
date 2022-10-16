package com.butramyou.listoffilms.service;

import android.content.Context;
import android.os.Vibrator;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.butramyou.listoffilms.R;
import com.butramyou.listoffilms.helpers.DatabaseHelper;
import com.butramyou.listoffilms.model.Film;

public class ItemListenerService {

    private final Context context;
    private final DatabaseHelper db;
    private static long lastClickTime = System.currentTimeMillis();
    private static int lastClickPosition = -1;

    public ItemListenerService(@NonNull Context context) {
        this.context = context;
        this.db = new DatabaseHelper(context);
    }

    public void longClick(View view) {
        final Vibrator vibe = (Vibrator) view.getContext().getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(80);
    }

    public void doubleTab(AdapterView<?> adapterView, View view, int position) {
        long currTime = System.currentTimeMillis();
        if (lastClickPosition == position && currTime - lastClickTime < ViewConfiguration.getDoubleTapTimeout()) {
            Film film = onItemDoubleClick(adapterView, position);
            refreshViewItem(view, film);
        }
        lastClickTime = currTime;
        lastClickPosition = position;
    }

    private void refreshViewItem(View view, Film film) {
        TextView someText = (TextView) view.findViewById(R.id.to_view_film_item_list);
        someText.setCompoundDrawablesRelativeWithIntrinsicBounds(
                0,
                0,
                film.isDownloaded() ? R.drawable.ic_downloaded : 0,
                0);
    }

    private Film onItemDoubleClick(AdapterView<?> adapterView, int position) {
        Film currentFilm = (Film) adapterView.getItemAtPosition(position);
        db.updateFilm(db.updatingDownloadStatus(currentFilm));
        return currentFilm;
    }
}
