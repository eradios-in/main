package com.rebellion.radioweb.controller;

import com.rebellion.radioweb.entity.BlogSitemapDto;
import com.rebellion.radioweb.entity.StationSitemapDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rebellion.radioweb.service.Impl.BlogServiceImpl;
import com.rebellion.radioweb.service.Impl.StationServiceImpl;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("")
public class MetaFileController  {

    private String baseUrl = "https://www.eradios.in";
    private final StationServiceImpl stationServiceImpl;
    private final BlogServiceImpl blogServiceImpl;

    public MetaFileController (StationServiceImpl stationServiceImpl, BlogServiceImpl blogServiceImpl) {
        this.stationServiceImpl = stationServiceImpl;
        this.blogServiceImpl = blogServiceImpl;
    }

    @GetMapping(value = "/sitemap.xml", produces = "application/xml")
    public void getSitemap(HttpServletResponse response) throws Exception {
        response.setContentType("application/xml");
        PrintWriter writer = response.getWriter();

        List<String> staticUrls = List.of(
            "", // home
            "stations",
            "contact",
            "add-station",
            "privacy",
            "about",
            "terms",
            "blogs"
        );

        LocalDate lastUpdateStatic = LocalDate.of(2025, 7,11);

        List<StationSitemapDto> dynamicStationUrls = stationServiceImpl.getStationsInfoForSitemap();
        List<BlogSitemapDto> dynamicBlogUrls = blogServiceImpl.getBlogsInfoForSitemap();

        writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        writer.println("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">");

        for (String path : staticUrls) {
            writer.println("  <url>");
            writer.println("    <loc>" + baseUrl + "/" + path + "</loc>");
            writer.println("    <lastmod>" + lastUpdateStatic + "</lastmod>");
            writer.println("    <changefreq>" + determineChangeFreq(path) + "</changefreq>");
            writer.println("    <priority>" + determinePriority(path) + "</priority>");
            writer.println("  </url>");
        }

        for (StationSitemapDto dto : dynamicStationUrls) {
            writer.println("  <url>");
            writer.println("    <loc>" + baseUrl + "/stations/" + dto.getFormattedName() + "</loc>");
            writer.println("    <lastmod>" + dto.getLastUpdateDate() + "</lastmod>"); // real value
            writer.println("    <changefreq>" + determineChangeFreq(dto.getFormattedName()) + "</changefreq>");
            writer.println("    <priority>" + determinePriority(dto.getFormattedName()) + "</priority>");
            writer.println("  </url>");
        }

        for (BlogSitemapDto dto : dynamicBlogUrls) {
            writer.println("  <url>");
            writer.println("    <loc>" + baseUrl + "/blogs/" + dto.getArticleUrl() + "</loc>");
            writer.println("    <lastmod>" + dto.getLastUpdateDate() + "</lastmod>"); // real value
            writer.println("    <changefreq>" + determineChangeFreq(dto.getArticleUrl()) + "</changefreq>");
            writer.println("    <priority>" + determinePriority(dto.getArticleUrl()) + "</priority>");
            writer.println("  </url>");
        }

        writer.println("</urlset>");
    }

    private String determineChangeFreq(String path) {
        if (path.startsWith("blogs") || path.startsWith("stations")) return "weekly";
        if (path.startsWith("about") || path.startsWith("contact") || path.equals("index")) return "yearly";
        return "monthly";
    }

    private String determinePriority(String path) {

        // Top-level categories or high-traffic pages
        if (path.startsWith("blogs") || path.startsWith("stations")) {
            return "1.0";
        }

        // Homepage or landing pages get high priority
        if (path.equals("") || path.equals("index") || path.equals("add-station")) {
            return "0.8";
        }

        // Frequently updated or important utility pages
        if (path.equals("contact") || path.equals("about")) {
            return "0.6";
        }

        // Low-priority or deep pages (archives, old posts, etc.)
        return "0.3";
    }


    @GetMapping(value = "/robots.txt", produces = "text/plain")
    public void getRobotsTxt(HttpServletResponse response) throws Exception {
        response.setContentType("text/plain");
        PrintWriter writer = response.getWriter();

        writer.println("User-agent: *");
        writer.println();

        writer.println("Disallow: /error");
        writer.println("Disallow: /filter");
        writer.println("Disallow: /add-blog");
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
