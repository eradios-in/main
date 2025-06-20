package com.rebellion.radioweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class RadiowebApplication {

	public static void main(String[] args) {
		SpringApplication.run(RadiowebApplication.class, args);
	}

}

// TODO: BUG :: Data Filtration
// TODO: BUG :: /stations/{name} is showing "Not Secure"
// TODO: BUG (IN END) :: /stations Border to each station like stations/{name}

// TODO: FEATURE :: listened count
// TODO: FEATURE :: errorCount for stations
// TODO: FEATURE :: ratings for stations
// TODO: FEATURE :: Create a /addStation controller
// TODO: FEATURE :: Backlink Generation Strategy
// TODO: FEATURE :: /blogs for SEO --> 1. Top 10 stations of MMM YYYY