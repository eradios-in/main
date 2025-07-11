package com.rebellion.radioweb.service;

import java.util.List;

import com.rebellion.radioweb.entity.BlogSitemapDto;
import org.springframework.http.ResponseEntity;
import com.rebellion.radioweb.entity.Blog;
import com.rebellion.radioweb.entity.StationOutDao;

public interface BlogService {
    Blog getBlogByArticleUrl(String articleUrl);
    Blog saveBlogArticle(Blog blogData);
    List<Blog> getAllBlogs();

    List<BlogSitemapDto> getBlogsInfoForSitemap();

    ResponseEntity<List<Blog>> getRelatedBlogs(String searchTags, String currentStationId);
    ResponseEntity<List<StationOutDao>> getRelatedStations(String searchTags, int currentStationId);
}
