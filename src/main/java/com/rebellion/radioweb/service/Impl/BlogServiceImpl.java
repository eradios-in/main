package com.rebellion.radioweb.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.rebellion.radioweb.entity.Blog;
import com.rebellion.radioweb.repo.BlogRepo;
import com.rebellion.radioweb.service.BlogService;

@Service
public class BlogServiceImpl implements BlogService{

    private BlogRepo blogRepo;

    public BlogServiceImpl(BlogRepo blogRepo) {
        this.blogRepo = blogRepo;
    }


    @Override
    public Blog getBlogByArticleUrl(String articleUrl) {
        Optional<Blog> foundBlog = blogRepo.findByArticleUrl(articleUrl);
        if(foundBlog.isPresent()){
            return foundBlog.get();
        }
        return new Blog();
    }


    @Override
    public Blog saveBlogArticle(Blog blogData) {
        Optional<Blog> foundBlog = blogRepo.findByArticleUrl(blogData.getArticleUrl());
        if(!foundBlog.isPresent()){
            blogData.setArticleUrl(blogData.getTitle());
            blogData.setRelatedStations(blogData.getRelatedStations().replace("  ", ""));;
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
    public List<Blog> getBlogsRelatedToStation(String formattedName) {
        Optional<List<Blog>> foundBlogs = blogRepo.findByRelatedStationsContainingIgnoreCase(formattedName);
        if(foundBlogs.isPresent()){
            return foundBlogs.get();
        }
        return new ArrayList<>();
    }

    

}
