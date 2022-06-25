package com.butramyou.listoffilms.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.butramyou.listoffilms.R;

public class ViewedFilmsFragment extends Fragment {

    public ViewedFilmsFragment() {
        // Required empty public constructor
    }

    public static ViewedFilmsFragment getInstance()    {
        return new ViewedFilmsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viwed, container, false);

        String[] films = {"Nikita", "Zorro", "Black Angel"};

        ListView listView = view.findViewById(R.id.to_view_films_list);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                films
        );

        listView.setAdapter(adapter);


        return view;
    }

}
