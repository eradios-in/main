package com.rebellion.radioweb.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rebellion.radioweb.entity.Station;

@Repository
public interface StationRepo extends JpaRepository<Station, Integer> {
    Page<Station> findAllByIsLiveTrue(Pageable pageable);
    List<Station> findFirst10ByIsLiveTrue();

    Optional<Station> findByFormattedName(String formattedName);

    Page<Station> findByIsLiveTrueAndNameContainingIgnoreCaseOrIsLiveTrueAndTagsContainingIgnoreCase(
            String name, String tags, Pageable pageable);

    List<String> findAllByIsLiveTrue(); // for sitemap controller

    @Query("SELECT s.formattedName FROM Station s WHERE s.isLive = true")
    List<String> findAllFormattedNamesByIsLiveTrue();
}
