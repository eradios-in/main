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
    List<Station> findFirst9ByOrderByLanguage();

    Optional<Station> findByFormattedName(String formattedName);

    @Query("SELECT DISTINCT s.language FROM Station s")
    List<String> findDistinctLanguages();

    @Query("SELECT DISTINCT s.state FROM Station s")
    List<String> findDistinctStates();

    @Query("SELECT DISTINCT s.genre FROM Station s")
    List<String> findDistinctGenres();

    Page<Station> findByNameContainingIgnoreCaseOrLanguageContainingIgnoreCaseOrGenreContainingIgnoreCaseOrStateContainingIgnoreCase(
            String name, String language, String genre, String state, Pageable pageable);

    @Query("SELECT CONCAT('stations/', s.formattedName) FROM Station s WHERE s.formattedName IS NOT NULL AND s.formattedName <> '' AND s.formattedName <> 'error'")
    List<String> findAllValidFormattedNames(); // for sitemap controller
}
