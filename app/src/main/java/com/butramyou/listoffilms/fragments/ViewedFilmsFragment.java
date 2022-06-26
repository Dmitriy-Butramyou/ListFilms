package com.butramyou.listoffilms.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.butramyou.listoffilms.R;
import com.butramyou.listoffilms.adapters.ToViewListAdapter;
import com.butramyou.listoffilms.helpers.DatabaseHelper;
import com.butramyou.listoffilms.model.Film;

import java.util.List;

public class ViewedFilmsFragment extends Fragment {

    public static ViewedFilmsFragment getInstance()    {
        return new ViewedFilmsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewed, container, false);
        DatabaseHelper db = new DatabaseHelper(view.getContext());
        List<Film> films = db.getFilms(true);

        ListView listView = view.findViewById(R.id.viewed_films_list);
        ToViewListAdapter adapter = new ToViewListAdapter(
                getContext(),
                R.layout.list_item_to_view,
                films,
                getFragmentManager());
        listView.setOnItemClickListener((parent, view1, position, id) ->
                Toast.makeText(getContext(), "Position: " + position, Toast.LENGTH_SHORT).show());
        listView.setAdapter(adapter);

        return view;
    }

}
