package com.rebellion.radioweb.entity;

import java.time.LocalDate;


public class BlogSitemapDto {
    String articleUrl;
    LocalDate lastUpdateDate;

    public BlogSitemapDto() {
    }

    public BlogSitemapDto(String articleUrl, LocalDate lastUpdateDate) {
        this.articleUrl = articleUrl;
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public LocalDate getLastUpdateDate() {
        return lastUpdateDate;
    }
}
