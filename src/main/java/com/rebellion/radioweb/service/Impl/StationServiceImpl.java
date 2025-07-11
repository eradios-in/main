package com.rebellion.radioweb.service.Impl;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.rebellion.radioweb.entity.StationSitemapDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rebellion.radioweb.entity.Station;
import com.rebellion.radioweb.entity.StationInDto;
import com.rebellion.radioweb.entity.StationOutDao;
import com.rebellion.radioweb.repo.StationRepo;
import com.rebellion.radioweb.service.StationService;

@Service
public class StationServiceImpl implements StationService {

    private final EmailService emailService;
    private final StationRepo stationRepo;
    private final ObjectMapper mapper;

    public StationServiceImpl(EmailService emailService, StationRepo stationRepo, ObjectMapper mapper) {
        this.emailService = emailService;
        this.stationRepo = stationRepo;
        this.mapper = mapper;
    }

    @Override
    public Page<Station> getAllStations(PageRequest pageRequest) {
        return stationRepo.findAll(pageRequest);
    }

    @Override
    public Page<StationOutDao> getAllStationsOut(PageRequest pageRequest) {
        Page<Station> stations = stationRepo.findAllByIsLiveTrue(pageRequest);
        return stations.map(station -> mapper.convertValue(station, StationOutDao.class));
    }

    @Override
    public List<StationOutDao> getRelatedStations(String searchTags, int currentStationId) {
        List<String> tagList = Arrays.stream(searchTags.split(","))
                .map(String::trim)
                .map(String::toLowerCase)
                .toList();

        List<StationOutDao> result = stationRepo.findAllByIsLiveTrue().stream()
                .filter(station -> {
                    if (station.getTags() == null || (station.getId() == currentStationId))
                        return false;
                    String tags = station.getTags().toLowerCase();
                    return tagList.stream().anyMatch(tags::contains);
                })
                .limit(10)
                .map(station -> mapper.convertValue(station, StationOutDao.class))
                .toList();

        return result;
    }

    @Override
    public ResponseEntity<Station> getStationById(int id) {
        Optional<Station> station = stationRepo.findById(id);
        if (station.isPresent()) {
            return new ResponseEntity<>(station.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Station> getStationByFormattedName(String formattedName) {
        Optional<Station> station = stationRepo.findByFormattedName(formattedName);
        if (station.isPresent() && station.get().getIsLive()) {
            return new ResponseEntity<>(station.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public List<StationSitemapDto> getStationsInfoForSitemap() {
        return stationRepo.findAllFormattedNamesAndLastUpdateDateByIsLiveTrue();
    }

    @Override
    public Station saveStation(Station input) {
        return stationRepo.save(input);
    }

    @Override
    public boolean sendContactEmail(Map<String, String> emailData) {
        if (emailData == null) {
            return false;
        }
        String message = "Sender's Name: " + emailData.get("fullName") + "\n"
                + "Sender's Email: " + emailData.get("email") + "\n"
                + "Subject: " + emailData.get("subject") + "\n"
                + "Message: " + emailData.get("content");

        // Send the email
        emailService.sendEmail(emailData.get("email"), emailData.get("subject"), message);
        return true;
    }

    @Override
    public Page<StationOutDao> searchStations(String name, String tags, Pageable pageable) {
        Page<Station> stations = stationRepo
                .findByIsLiveTrueAndNameContainingIgnoreCaseOrIsLiveTrueAndTagsContainingIgnoreCase(
                        name, tags, pageable);
        return stations.map(station -> mapper.convertValue(station, StationOutDao.class));
    }

    @Override
    public boolean addStationRequest(StationInDto input) {
        // User submits a station via form.
        // Submit for review if stream URL is valid.
        // Send a confirmation email if accepted.
        RestTemplate restTemplate = new RestTemplate();
        HttpStatusCode status = restTemplate.getForEntity(input.getUrl(), Void.class).getStatusCode();
        if (status.is2xxSuccessful()) {
            String content = String.format(
                    "Email: %s\nStation Name: %s\nFav Url: %s\nStream Url: %s\nTags: %s\nComment: %s", input.getEmail(),
                    input.getName(), input.getFavicon_url(), input.getUrl(), input.getTags(), input.getComment());
            emailService.sendEmail(input.getEmail(), "Add Station: " + input.getName(), content);
            return true;
        }
        return false;
    }

    // ************************************* DANGER ZONE
    // ******************************************
    /**
     * Fetches stations from an external API and saves them to the repository.
     * 
     * @return List of Station objects fetched from the API.
     */
    @Override
    public List<Station> fetchStationsFromAPI() {
        List<Station> stations = new ArrayList<>();
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder()
                .uri(java.net.URI.create("http://162.55.180.156/json/stations/bycountrycodeexact/in"))
                .header("Accept", "application/json")
                .build();
        HttpResponse<String> response;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                stations = mapper.readValue(response.body(), new TypeReference<>() {
                });
                for (Station station : stations) {
                    System.out.println(station);
                    stationRepo.save(station);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stations;
    }
}
