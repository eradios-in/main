package com.rebellion.radioweb.service.Impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rebellion.radioweb.entity.Blog;
import com.rebellion.radioweb.entity.StationOutDao;
import com.rebellion.radioweb.repo.BlogRepo;
import com.rebellion.radioweb.service.BlogService;

@Service
public class BlogServiceImpl implements BlogService {

    private final BlogRepo blogRepo;
    private final StationServiceImpl stationServiceImpl;

    public BlogServiceImpl(BlogRepo blogRepo, StationServiceImpl stationServiceImpl) {
        this.blogRepo = blogRepo;
        this.stationServiceImpl = stationServiceImpl;
    }

    @Override
    public Blog getBlogByArticleUrl(String articleUrl) {
        Optional<Blog> foundBlog = blogRepo.findByArticleUrl(articleUrl);
        if (foundBlog.isPresent()) {
            return foundBlog.get();
        }
        return new Blog();
    }

    @Override
    public Blog saveBlogArticle(Blog blogData) {
        Optional<Blog> foundBlog = blogRepo.findByArticleUrl(blogData.getArticleUrl());
        if (!foundBlog.isPresent()) {
            blogData.setArticleUrl(blogData.getTitle());
            return blogRepo.save(blogData);
        }
        return new Blog();
    }

    @Override
    public List<Blog> getAllBlogs() {
        return blogRepo.findAll();
    }

    @Override
    public List<String> getListOfBlogsForSitemap() {
        return blogRepo.findAllArticleUrls();
    }

    @Override
    public ResponseEntity<List<Blog>> getRelatedBlogs(String searchTags, String currentStationId) {
        List<String> tagList = Arrays.stream(searchTags.split(","))
                .map(String::trim)
                .map(String::toLowerCase)
                .toList();

        List<Blog> blogs = blogRepo.findAll().stream()
                .filter(blog -> {
                    if (blog.getTags() == null || blog.getArticleUrl().equals(currentStationId))
                        return false;
                    String tags = blog.getTags().toLowerCase();
                    return tagList.stream().anyMatch(tags::contains);
                })
                .limit(10)
                .toList();
        return new ResponseEntity<>(blogs, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<StationOutDao>> getRelatedStations(String tags, int currentStationId) {
        List<StationOutDao> stationOutDaos = stationServiceImpl.getRelatedStations(tags, currentStationId);
        return new ResponseEntity<>(stationOutDaos, HttpStatus.OK);
    }
}
