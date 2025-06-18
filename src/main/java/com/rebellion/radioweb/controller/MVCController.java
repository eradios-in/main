package com.rebellion.radioweb.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.rebellion.radioweb.entity.Station;
import com.rebellion.radioweb.service.Impl.StationServiceImpl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("")
public class MVCController {

    private StationServiceImpl stationServiceImpl;

    public MVCController(StationServiceImpl stationServiceImpl) {
        this.stationServiceImpl = stationServiceImpl;
    }

    @GetMapping("")
    public String index() {
        return "index";
    }

    @GetMapping("/error")
    public String showErrorPage() {
        return "error";
    }

    @GetMapping("stations")
    public ModelAndView getStationsPage(ModelAndView modelAndView) {
        // modelAndView.addObject("filters", stationServiceImpl.getAllFilters());
        modelAndView.setViewName("stations");
        return modelAndView;
    }

    @GetMapping("stations/{formattedName}")
    public ModelAndView getStation(@PathVariable String formattedName, ModelAndView modelAndView) {
        Station station = stationServiceImpl.getStationByFormattedName(formattedName).getBody();
        List<String> tags = null;
        if(station != null) {
             tags = List.of(station.getTags().split(",")).stream().map(String::trim).toList();
        }
        modelAndView.addObject("station", station);
        modelAndView.addObject("tags", tags);
        modelAndView.addObject("related", stationServiceImpl.getRelatedStations().getBody());
        modelAndView.setViewName("station");
        return modelAndView;
    }

    @GetMapping("/privacy")
    public String getPrivacyPolicyPage() {
        return "privacy";
    }

    @GetMapping("/about")
    public String getAboutUsPage() {
        return "about";
    }

    @GetMapping("/contact")
    public String getContactUsPage() {
        return "contact";
    }

    @GetMapping("/terms")
    public String getTermsOfUsagePage() {
        return "terms";
    }

    @GetMapping("/filter")
    public ModelAndView getDataFilterPage(ModelAndView modelAndView, 
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "1") int limit, 
            @RequestParam(defaultValue = "name") String sortBy) {
        PageRequest pageRequest = PageRequest.of(page - 1, limit, Sort.by(sortBy));
        Page<Station> response = stationServiceImpl.getAllStations(pageRequest);
        List<Station> list = response.getContent();
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", response.getTotalPages());
        modelAndView.addObject("data", list);
        modelAndView.setViewName("filter");
        return modelAndView;
    }

}
