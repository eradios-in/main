package com.rebellion.radioweb.entity;

public class StationOutDao {

    private int id;
    private String name;
    private String formattedName;
    private String favicon;
    private String tags;
    private String metaDescription;

    public StationOutDao() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFormattedName() {
        return formattedName;
    }

    public String getFavicon() {
        return favicon;
    }

    public String getTags() {
        return tags;
    }

    public String getMetaDescription() {
        return metaDescription;
    }

}
