package com.rebellion.radioweb.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@JsonIgnoreProperties
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private boolean isLive;
    private String name="error";
    private String formattedName="error";
    private String metaDescription;
    @Column(length = 4096)
    private String about;
    @Column(length = 2048)
    private String url_resolved;
    @Column(length = 2048)
    private String homepage;
    @Column(length = 1024)
    private String tags;
    @Column(length = 2048)
    private String favicon = "/images/station_fallback_image.webp";

    public Station() {
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public boolean getIsLive() {
        return isLive;
    }

    public void setIsLive(boolean isLive) {
        this.isLive = isLive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl_resolved() {
        return url_resolved;
    }

    public void setUrl_resolved(String url_resolved) {
        this.url_resolved = url_resolved;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getFavicon() {
        return favicon;
    }

    public void setFavicon(String favicon) {
        this.favicon = favicon;
    }

    public String getFormattedName() {
        return formattedName;
    }

    public void setFormattedName(String name) {
        this.formattedName = name.trim().toLowerCase().replaceAll(" ", "-");
    }
}
