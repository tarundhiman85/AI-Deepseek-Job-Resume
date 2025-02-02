package com.tarun.DeepSeekJobFinder.controller;

import com.tarun.DeepSeekJobFinder.service.RssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rss")
public class RssController {

    @Autowired
    private RssService rssService;

    @PostMapping("/fetch")
    public List<Map<String, Object>> fetchRss(@RequestParam String feedUrl) {
        return rssService.fetchRssFeed(feedUrl);
    }
}
