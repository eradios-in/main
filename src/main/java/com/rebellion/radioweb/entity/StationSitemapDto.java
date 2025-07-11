package com.rebellion.radioweb.entity;

import java.time.LocalDate;


public class StationSitemapDto {
    String formattedName;
    LocalDate lastUpdateDate;

    public StationSitemapDto() {
    }

    public StationSitemapDto(String formattedName, LocalDate lastUpdateDate) {
        this.formattedName = formattedName;
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getFormattedName() {
        return formattedName;
    }

    public LocalDate getLastUpdateDate() {
        return lastUpdateDate;
    }
}
