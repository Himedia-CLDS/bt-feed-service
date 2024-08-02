package com.cdls.bottletalk.feed.controller;

import com.cdls.bottletalk.feed.model.FeedDTO;
import com.cdls.bottletalk.feed.service.FeedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1/feed")
@Slf4j
public class FeedController {

    private FeedService service;

    public FeedController(FeedService service) {
        this.service = service;
    }

    @GetMapping
    public List<FeedDTO> getList(@RequestParam(required = false) String userId){
        List<FeedDTO> feedList = service.findAllFeed(userId);

        return feedList;
    }
}