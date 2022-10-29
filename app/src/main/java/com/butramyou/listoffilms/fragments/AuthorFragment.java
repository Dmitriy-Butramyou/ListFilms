package com.butramyou.listoffilms.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.butramyou.listoffilms.R;

public class AuthorFragment extends Fragment {

    public static AuthorFragment getInstance() {
        return new AuthorFragment();
    }

    public AuthorFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_author, container, false);
    }
}