package com.rebellion.radioweb.controller;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rebellion.radioweb.service.Impl.StationServiceImpl;

import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("")
public class MetaFileController  {

    private String baseUrl = "https://www.eradios.in";
    private final StationServiceImpl stationServiceImpl;

    @Autowired
    public MetaFileController (StationServiceImpl stationServiceImpl) {
        this.stationServiceImpl = stationServiceImpl;
    }

    @GetMapping(value = "/sitemap.xml", produces = "application/xml")
    public void getSitemap(HttpServletResponse response) throws Exception {
        response.setContentType("application/xml");
        PrintWriter writer = response.getWriter();

        List<String> staticUrls = List.of(
            "", // home
            "stations",
            "contact",
            "privacy",
            "about",
            "terms"
        );

        // Simulated dynamic URLs (e.g., fetched from DB)
        List<String> dynamicUrls = stationServiceImpl.getListOfAllValidStationFormattedNames();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        writer.println("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">");

        for (String path : staticUrls) {
            writer.println("  <url>");
            writer.println("    <loc>" + baseUrl + "/" + path + "</loc>");
            writer.println("    <lastmod>" + LocalDateTime.now().format(formatter) + "</lastmod>");
            writer.println("    <changefreq>weekly</changefreq>");
            writer.println("    <priority>0.8</priority>");
            writer.println("  </url>");
        }

        for (String path : dynamicUrls) {
            writer.println("  <url>");
            writer.println("    <loc>" + baseUrl + "/" + path + "</loc>");
            writer.println("    <lastmod>" + LocalDateTime.now().format(formatter) + "</lastmod>");
            writer.println("    <changefreq>daily</changefreq>");
            writer.println("    <priority>0.6</priority>");
            writer.println("  </url>");
        }

        writer.println("</urlset>");
    }

    @GetMapping(value = "/robots.txt", produces = "text/plain")
    public void getRobotsTxt(HttpServletResponse response) throws Exception {
        response.setContentType("text/plain");
        PrintWriter writer = response.getWriter();

        writer.println("User-agent: *");
        writer.println();
        writer.println("Sitemap: " + baseUrl + "/sitemap.xml");

        writer.flush();
    }

    @GetMapping(value = "/ads.txt", produces = "text/plain")
    public void getAdsTxt(HttpServletResponse response) throws Exception {
        response.setContentType("text/plain");
        PrintWriter writer = response.getWriter();

        writer.println("google.com, pub-2939946349164066, DIRECT, f08c47fec0942fa0");

        writer.flush();
    }
}
