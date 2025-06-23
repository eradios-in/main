package com.rebellion.radioweb.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import com.rebellion.radioweb.entity.Station;
import com.rebellion.radioweb.entity.StationInDto;
import com.rebellion.radioweb.entity.StationOutDao;

public interface StationService {
    ResponseEntity<List<StationOutDao>> getRelatedStations();
    List<Station> fetchStationsFromAPI();
    Page<Station> getAllStations(PageRequest pageRequest);
    Page<StationOutDao> getAllStationsOut(PageRequest pageRequest);
    ResponseEntity<Station> getStationByFormattedName(String formattedName);
    // Map<String, List<String>> getAllFilters();
    Station saveStation(Station station);
    boolean sendContactEmail(Map<String, String> emailData);
    Page<StationOutDao> searchStations(String name, String tags, Pageable pageable);
    List<String> getListOfAllValidStationFormattedNames();
    boolean addStationRequest(StationInDto stationInDto);
}
