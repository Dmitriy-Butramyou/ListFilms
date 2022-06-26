package com.butramyou.listoffilms.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.butramyou.listoffilms.R;

public class AddFilmFragment extends Fragment {

    public static AddFilmFragment getInstance()    {
        return new AddFilmFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_add_film, container, false);
    }
}
