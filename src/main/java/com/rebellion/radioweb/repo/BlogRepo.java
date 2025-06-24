package com.rebellion.radioweb.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rebellion.radioweb.entity.Blog;

@Repository
public interface BlogRepo extends JpaRepository<Blog, Integer> {
    Optional<Blog> findByArticleUrl(String articleUrl);
}
