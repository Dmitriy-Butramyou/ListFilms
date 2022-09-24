package com.butramyou.listoffilms.model;

public class Film {

    private int id;
    private String name;
    private Boolean isViewed;
    private Boolean isDownloaded;

    public Film() {
    }

    public Film(String name, Boolean isViewed, Boolean isDownloaded) {
        this.name = name;
        this.isViewed = isViewed;
        this.isDownloaded = isDownloaded;
    }

    public Film(int id, String name, Boolean isViewed, Boolean isDownloaded) {
        this.id = id;
        this.name = name;
        this.isViewed = isViewed;
        this.isDownloaded = isDownloaded;
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

    public Boolean isDownloaded() {
        return isDownloaded;
    }

    public void setDownloaded(Boolean isDownloaded) {
        this.isDownloaded = isDownloaded;
    }
}
