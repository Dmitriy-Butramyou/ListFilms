package com.butramyou.listoffilms.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.butramyou.listoffilms.R;

public class ToViewFilmsFragment extends Fragment {

    public ToViewFilmsFragment() {
        // Required empty public constructor
    }

    public static ToViewFilmsFragment getInstance()    {
        return new ToViewFilmsFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_to_view, container, false);
        String[] films = {"007", "Borne", "Spider-man"};

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
