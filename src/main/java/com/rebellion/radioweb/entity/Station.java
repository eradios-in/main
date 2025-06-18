package com.rebellion.radioweb.entity;

import java.util.Optional;

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
    private String name="error";
    private String formattedName="error";
    private String metaDescription;
    @Column(length = 4096)
    private String about;
    @Column(length = 2048)
    private String url_resolved;
    @Column(length = 2048)
    private String homepage;
    private String tags;
    @Column(length = 2048)
    private String favicon = "/images/station_fallback_image.webp";
    private String state;
    private String language;
    private String genre;

    public Station() {
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
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

    public void setGenre(String genre) {
        this.genre = genre;
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

    public void setTags(String language, String genre, String state) {
        this.tags = String.join(", ", Optional.ofNullable(language).orElse(""), Optional.ofNullable(genre).orElse(""), Optional.ofNullable(state).orElse(""));
    }

    public String getFavicon() {
        return favicon;
    }

    public void setFavicon(String favicon) {
        this.favicon = favicon;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }


    public String getFormattedName() {
        return formattedName;
    }

    public void setFormattedName(String name) {
        this.formattedName = name.trim().toLowerCase().replaceAll(" ", "-");
    }

}
