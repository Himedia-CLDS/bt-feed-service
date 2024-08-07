package com.cdls.bottletalk.feed.controller;

import com.cdls.bottletalk.feed.model.Feed;
import com.cdls.bottletalk.feed.model.FeedDTO;
import com.cdls.bottletalk.feed.model.FeedLike;
import com.cdls.bottletalk.feed.service.FeedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    @PostMapping("insert")
    public void insert(@RequestBody FeedDTO feedDTO){
        feedDTO.setCreatedAt(LocalDateTime.now());
        service.insertFeed(feedDTO);
    }

    @PutMapping("update")
    public FeedDTO update(@RequestBody FeedDTO feedDTO){
        service.updateFeed(feedDTO);
        return null;
    }

    @PutMapping("delete")
    public FeedDTO delete(@RequestBody FeedDTO feedDTO){
        service.deleteFeed(feedDTO);
        return null;
    }

    @PutMapping("like")
    public FeedLike like(@RequestBody FeedLike feedLike){
        FeedLike likeDto = service.likeFeed(feedLike);
        return likeDto;
    }

    @PostMapping("like")
    public List<FeedDTO> getLike(@RequestParam("userId") String userId){
        List<FeedDTO> likeList = service.findLikedFeed(userId);
        return likeList;
    }
}