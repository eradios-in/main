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

// TODO: DB :: Data Filtration
// TODO: SEO: Fix meta tags for pages


// TODO: VIEW :: Implement errorCount for stations
// TODO: VIEW :: Implement ratings for stations
// TODO: VIEW :: Implement search feature
