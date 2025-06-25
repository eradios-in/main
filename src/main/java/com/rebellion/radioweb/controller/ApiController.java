package com.rebellion.radioweb.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rebellion.radioweb.entity.Station;
import com.rebellion.radioweb.entity.StationOutDao;
import com.rebellion.radioweb.service.Impl.StationServiceImpl;

@RestController
@RequestMapping("api")
public class ApiController {

    private StationServiceImpl stationServiceImpl;

    @Autowired
    public ApiController(StationServiceImpl stationServiceImpl) {
        this.stationServiceImpl = stationServiceImpl;
    }

    @GetMapping("/status")
    public HashMap<String, String> keepRenderAlive(){
        HashMap<String, String> map = new HashMap<>();
        map.put("Status", "Up and Running");
        return map;
    }

    @GetMapping("/stations")
    public ResponseEntity<Map<String, Object>> getStationsApi(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "60") int size, // <-- add this
            @RequestParam(defaultValue = "name") String sortBy) {
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(sortBy));
        Page<StationOutDao> response = stationServiceImpl.getAllStationsOut(pageRequest);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("data", response.getContent());
        responseBody.put("currentPage", page);
        responseBody.put("totalPages", response.getTotalPages());
        responseBody.put("totalItems", response.getTotalElements());
        return ResponseEntity.ok(responseBody);
    }

    // a get endpoint tof fetch stations filtered by language, genre, and state
    @GetMapping("/stations/search")
public ResponseEntity<Map<String, Object>> searchStations(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "60") int size,
        @RequestParam String query) {
    PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("name"));
    Page<StationOutDao> response = stationServiceImpl.searchStations(query, query, pageRequest);
    Map<String, Object> responseBody = new HashMap<>();
    responseBody.put("data", response.getContent());
    responseBody.put("currentPage", page);
    responseBody.put("totalPages", response.getTotalPages());
    responseBody.put("totalItems", response.getTotalElements());
    return ResponseEntity.ok(responseBody);
}

    @PutMapping("/filter")
    public Station postDataFilterPage(@RequestBody Station input) {
        // capitalize the first letter of each word in the name
        if (input.getName() != null && !input.getName().isEmpty()) {
            String[] words = input.getName().split(" ");
            StringBuilder formattedName = new StringBuilder();
            for (String word : words) {
                formattedName.append(word.substring(0, 1).toUpperCase()).append(word.substring(1).toLowerCase())
                        .append(" ");
            }
            input.setFormattedName(formattedName.toString().trim());
        }
        // formtedName is used to create a URL-friendly version of the station name
        if (input.getFormattedName() == null || input.getFormattedName().isEmpty()) {
            input.setFormattedName(input.getName().trim().toLowerCase().replaceAll(" ", "-"));
        }
        // set tags for the station
        if (input.getTags() == null || input.getTags().isEmpty()) {
            input.setTags(input.getTags());
        }
        // set favicon URL if not provided
        if (input.getFavicon() == null || input.getFavicon().isEmpty()) {
            input.setFavicon("/images/station_fallback_image.webp");
        }
        // set homepage URL if not provided
        if (input.getHomepage() == null || input.getHomepage().isEmpty()) {
            input.setHomepage("https://www.google.com/search?q=" + input.getName());
        }
        // set url_resolved if not provided
        if (input.getUrl_resolved() == null || input.getUrl_resolved().isEmpty()) {
            input.setUrl_resolved("");
        }

        // save input station to the database
        return stationServiceImpl.saveStation(input);
    }

    @PostMapping("/email")
    public ResponseEntity<String> sendEmail(@RequestBody Map<String, String> emailData) {
        // Call the service to send the email
        boolean isSent = stationServiceImpl.sendContactEmail(emailData);
        if (isSent) {
            return ResponseEntity.ok("Email sent successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email");
        }
    }
}