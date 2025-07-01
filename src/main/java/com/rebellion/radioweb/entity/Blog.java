package com.rebellion.radioweb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "blogs")
public class Blog {

    private String faviconUrl;
    private String title;
    @Column(length=4096)
    private String content;
    private String metaDesc;
    @Id
    private String articleUrl;
    @Column(length=4096)
    private String relatedStations;

    public Blog() {
    }

    public String getMetaDesc() {
        return metaDesc;
    }

    public void setMetaDesc(String metaDesc) {
        this.metaDesc = metaDesc;
    }

    public String getFaviconUrl() {
        return faviconUrl;
    }

    public void setFaviconUrl(String faviconUrl) {
        this.faviconUrl = faviconUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String title) {
        this.articleUrl = title.replace(" ", "-").toLowerCase();
    }

    public String getRelatedStations() {
        return relatedStations;
    }

    public void setRelatedStations(String relatedStations) {
        this.relatedStations = relatedStations;
    }
}
