package com.rebellion.radioweb.repo;

import java.util.List;
import java.util.Optional;

import com.rebellion.radioweb.entity.StationSitemapDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rebellion.radioweb.entity.Station;

@Repository
public interface StationRepo extends JpaRepository<Station, Integer> {
    Page<Station> findAllByIsLiveTrue(Pageable pageable);

    Optional<Station> findByFormattedName(String formattedName);

    Page<Station> findByIsLiveTrueAndNameContainingIgnoreCaseOrIsLiveTrueAndTagsContainingIgnoreCase(
            String name, String tags, Pageable pageable);

    List<Station> findAllByIsLiveTrue();

    @Query("SELECT s.formattedName FROM Station s WHERE s.isLive = true")
    List<String> findAllFormattedNamesByIsLiveTrue();

    @Query("SELECT new com.rebellion.radioweb.entity.StationSitemapDto(s.formattedName, s.lastUpdateDate) " +
            "FROM Station s WHERE s.isLive = true")
    List<StationSitemapDto> findAllFormattedNamesAndLastUpdateDateByIsLiveTrue();
}
