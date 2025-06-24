package com.rebellion.radioweb.service;

import java.util.List;

import com.rebellion.radioweb.entity.Blog;

public interface BlogService {
    Blog getBlogByArticleUrl(String articleUrl);
    Blog saveBlogArticle(Blog blogData);
    List<Blog> getAllBlogs();
}
