package com.rebellion.radioweb.repo;

import java.util.List;
import java.util.Optional;

import com.rebellion.radioweb.entity.BlogSitemapDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.rebellion.radioweb.entity.Blog;

@Repository
public interface BlogRepo extends JpaRepository<Blog, String> {
    Optional<Blog> findByArticleUrl(String articleUrl);

    @Query("SELECT b.articleUrl FROM Blog b")
    List<String> findAllArticleUrls();

    @Query("SELECT new com.rebellion.radioweb.entity.BlogSitemapDto(a.articleUrl, a.lastUpdateDate) FROM Blog a")
    List<BlogSitemapDto> findArticleUrlAndLastUpdateDate();
}
