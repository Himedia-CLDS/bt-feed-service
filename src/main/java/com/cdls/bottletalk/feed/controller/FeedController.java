package com.cdls.bottletalk.feed.controller;

import com.cdls.bottletalk.feed.model.FeedDTO;
import com.cdls.bottletalk.feed.model.FeedLike;
import com.cdls.bottletalk.feed.service.FeedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("like")
    public FeedLike like(@RequestBody FeedLike feedLike){
        FeedLike likeDto = service.likeFeed(feedLike);
        return likeDto;
    }
}