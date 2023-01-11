package com.butramyou.listoffilms.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.butramyou.listoffilms.R;
import com.butramyou.listoffilms.adapters.ToViewListAdapter;
import com.butramyou.listoffilms.helpers.DatabaseHelper;
import com.butramyou.listoffilms.helpers.FilmsComparator;
import com.butramyou.listoffilms.model.Film;
import com.butramyou.listoffilms.service.ItemListenerService;

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
        List<Film> films = db.getFilmsByStatus(true);
        FilmsComparator filmsComparator = new FilmsComparator();
        films.sort(filmsComparator);

        ListView listView = view.findViewById(R.id.viewed_films_list);
        ToViewListAdapter adapter = new ToViewListAdapter(
                getContext(),
                R.layout.list_item_to_view,
                films,
                getFragmentManager());
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            ItemListenerService service = new ItemListenerService(getContext());
            service.doubleTab(parent, view1, position);
        });
        listView.setOnItemLongClickListener((parent, currentView, position, id) -> {
            ItemListenerService service = new ItemListenerService(getContext());
            service.longClick(currentView);
            openFilmFragment(parent, position);
            return true;
        });

        listView.setAdapter(adapter);

        return view;
    }

    private void openFilmFragment(AdapterView<?> adapterView, int position) {
        Film currentFilm = (Film) adapterView.getItemAtPosition(position);
        FilmFragment filmFragment = new FilmFragment(currentFilm, false);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, filmFragment);
        fragmentTransaction.commit();
    }
}
