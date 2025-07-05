package com.rebellion.radioweb.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.rebellion.radioweb.entity.Blog;
import com.rebellion.radioweb.entity.Station;
import com.rebellion.radioweb.entity.StationInDto;
import com.rebellion.radioweb.service.Impl.BlogServiceImpl;
import com.rebellion.radioweb.service.Impl.StationServiceImpl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("")
public class MVCController {

    private StationServiceImpl stationServiceImpl;
    private BlogServiceImpl blogServiceImpl;

    public MVCController(StationServiceImpl stationServiceImpl, BlogServiceImpl blogServiceImpl) {
        this.stationServiceImpl = stationServiceImpl;
        this.blogServiceImpl = blogServiceImpl;
    }

    @GetMapping("")
    public String index() {
        return "index";
    }

    @GetMapping("/error")
    public String showErrorPage() {
        return "error";
    }

    @GetMapping("/stations")
    public String getStationsPage() {
        return "stations";
    }

    @GetMapping("/stations/{formattedName}")
    public ModelAndView getStation(@PathVariable String formattedName, ModelAndView modelAndView) {
        Station station = stationServiceImpl.getStationByFormattedName(formattedName).getBody();
        List<String> tags = null;
        if(station != null) {
             tags = List.of(station.getTags().split(",")).stream().map(String::trim).toList();
        }
        modelAndView.addObject("station", station);
        modelAndView.addObject("tags", tags);
        modelAndView.addObject("related", stationServiceImpl.getRelatedStations().getBody());
        modelAndView.addObject("relatedBlogs", stationServiceImpl.getRelatedBlogs(formattedName));
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
            @RequestParam(defaultValue = "id") String sortBy) {
        PageRequest pageRequest = PageRequest.of(page - 1, limit, Sort.by(sortBy));
        Page<Station> response = stationServiceImpl.getAllStations(pageRequest);
        List<Station> list = response.getContent();
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", response.getTotalPages());
        modelAndView.addObject("data", list);
        modelAndView.setViewName("filter");
        return modelAndView;
    }

    @GetMapping("add-station")
    public String getAddStationPage(){
        return "add-station";
    }

    @PostMapping("/add-station")
    public ModelAndView addStation(@RequestBody StationInDto input, ModelAndView modelAndView){
        stationServiceImpl.addStationRequest(input);
        return modelAndView;
    }

    @GetMapping("/blogs")
    public ModelAndView getBlogsPage(ModelAndView modelAndView){
        modelAndView.addObject("blogs", blogServiceImpl.getAllBlogs());
        return modelAndView;
    }

    @PostMapping("/blogs")
    public ResponseEntity<Blog> postBlog(@RequestBody Blog blogData){
        return ResponseEntity.ok(blogServiceImpl.saveBlogArticle(blogData));
    }

    @GetMapping("/blogs/{articleUrl}")
    public ModelAndView getBlogArticle(@PathVariable String articleUrl, ModelAndView modelAndView){
        modelAndView.addObject("blog", blogServiceImpl.getBlogByArticleUrl(articleUrl));
        modelAndView.setViewName("blog");
        return modelAndView;
    }

    @GetMapping("/add-blog")
    public String getAddBlogForm(){
        return "add-blog";
    }
}
