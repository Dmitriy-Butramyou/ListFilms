package com.butramyou.listoffilms.adapters;

import android.content.Context;
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
import com.butramyou.listoffilms.fragments.ToViewFilmsFragment;
import com.butramyou.listoffilms.fragments.ViewedFilmsFragment;
import com.butramyou.listoffilms.helpers.DatabaseHelper;
import com.butramyou.listoffilms.model.Film;

import java.util.List;

public class ToViewListAdapter extends ArrayAdapter<Film> {
    private int layout;
    private FragmentManager fragmentManager;
    public ToViewListAdapter(@NonNull Context context, int resource, List<Film> films, FragmentManager fragment) {
        super(context, resource, films);
        layout = resource;
        fragmentManager = fragment;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout, parent, false);
            ViewHolder viewHolder = new ViewHolder();

            viewHolder.textView = convertView.findViewById(R.id.to_view_film_item_list);
            viewHolder.changeStatusBtn = convertView.findViewById(R.id.change_watched_status);
            viewHolder.deleteBtn = convertView.findViewById(R.id.delete_film);

            Film currentFilm = getItem(position);

            viewHolder.changeStatusBtn.setImageResource(currentFilm.isViewed() ? R.drawable.ic_to_view : R.drawable.ic_viewed);
            viewHolder.textView.setText(currentFilm.getName());

            viewHolder.changeStatusBtn.setOnClickListener(v -> {
                DatabaseHelper db = new DatabaseHelper(getContext());
                db.updateFilm(db.updateViewedStatus(currentFilm));
                recreateCurrentFragment(!currentFilm.isViewed());
            });

            viewHolder.deleteBtn.setOnClickListener(v -> {
                DatabaseHelper db = new DatabaseHelper(getContext());
                db.deleteFilm(currentFilm.getId());
                recreateCurrentFragment(currentFilm.isViewed());
            });
            convertView.setTag(viewHolder);
        } else {
            ViewHolder mainHolder = (ViewHolder) convertView.getTag();
            mainHolder.textView.setText(getItem(position).getName());
        }

        return convertView;
    }

    private void recreateCurrentFragment(boolean isViewed) {
        if (isViewed) {
            fragmentManager.beginTransaction().replace(R.id.main_frame, ViewedFilmsFragment.getInstance()).commit();
        } else {
            fragmentManager.beginTransaction().replace(R.id.main_frame, ToViewFilmsFragment.getInstance()).commit();
        }
    }

    public class ViewHolder {
        TextView textView;
        ImageButton changeStatusBtn;
        ImageButton deleteBtn;
    }
}
