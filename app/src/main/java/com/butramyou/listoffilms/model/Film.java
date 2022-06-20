package com.butramyou.listoffilms.model;

public class Film {

    private int id;
    private String name;
    private Boolean isViewed;

    public Film() {
    }

    public Film(String name, Boolean isViewed) {
        this.name = name;
        this.isViewed = isViewed;
    }

    public Film(int id, String name, Boolean isViewed) {
        this.id = id;
        this.name = name;
        this.isViewed = isViewed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isViewed() {
        return isViewed;
    }

    public void setViewed(Boolean viewed) {
        isViewed = viewed;
    }
}
