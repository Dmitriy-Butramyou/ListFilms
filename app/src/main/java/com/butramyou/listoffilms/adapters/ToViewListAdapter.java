package com.butramyou.listoffilms.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.butramyou.listoffilms.R;
import com.butramyou.listoffilms.helpers.DatabaseHelper;
import com.butramyou.listoffilms.model.Film;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

public class ToViewListAdapter extends ArrayAdapter<Film> {
    private final int layout;
    private final FragmentManager fragmentManager;

    public ToViewListAdapter(@NonNull Context context, int resource, List<Film> films, FragmentManager fragment) {
        super(context, resource, films);
        layout = resource;
        fragmentManager = fragment;
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(layout, parent, false);
        ViewHolder viewHolder = new ViewHolder();

        viewHolder.textView = convertView.findViewById(R.id.to_view_film_item_list);
        viewHolder.changeStatusBtn = convertView.findViewById(R.id.change_watched_status);
        viewHolder.deleteBtn = convertView.findViewById(R.id.delete_film);

        Film currentFilm = getItem(position);

        viewHolder.changeStatusBtn.setImageResource(currentFilm.isViewed() ? R.drawable.ic_to_view : R.drawable.ic_viewed);
        viewHolder.textView.setText(currentFilm.getName());
        if (currentFilm.isDownloaded()) {
            viewHolder.textView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_downloaded, 0);
        }

        viewHolder.changeStatusBtn.setOnClickListener(v -> {
            DatabaseHelper db = new DatabaseHelper(getContext());
            db.updateFilm(db.updateViewedStatus(currentFilm));
            remove(currentFilm);
        });

        viewHolder.deleteBtn.setOnClickListener(v -> new MaterialAlertDialogBuilder(getContext())
                .setMessage(Html.fromHtml(v.getContext().getString(R.string.is_delete_film, currentFilm.getName()), Build.VERSION.SDK_INT))
                .setPositiveButton(v.getContext().getString(R.string.ok), (dialog, id) -> {
                    DatabaseHelper db = new DatabaseHelper(getContext());
                    db.deleteFilm(currentFilm.getId());
                    remove(currentFilm);
                })
                .setNegativeButton(v.getContext().getString(R.string.cancel), (dialog, id) -> dialog.cancel())
                .show());
        convertView.setTag(viewHolder);

        return convertView;
    }

    private class ViewHolder {
        TextView textView;
        ImageButton changeStatusBtn;
        ImageButton deleteBtn;
    }
}
