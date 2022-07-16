package com.butramyou.listoffilms.helpers;

import com.butramyou.listoffilms.model.Film;

import java.util.Comparator;

public class FilmsComparator implements Comparator<Film> {

    @Override
    public int compare(Film film1, Film film2) {
        if (film1.getName().matches("\\d+") && film2.getName().matches("\\d+")) {
           return Integer.parseInt(film1.getName()) - Integer.parseInt(film2.getName());
        }
        return film1.getName().compareToIgnoreCase(film2.getName());
    }
}
