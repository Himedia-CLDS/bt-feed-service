package com.cdls.bottletalk.feed.service;

import com.cdls.bottletalk.feed.model.Feed;
import com.cdls.bottletalk.feed.model.FeedDTO;
import com.cdls.bottletalk.feed.repository.FeedRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedService {

    private FeedRepository repository;

    public FeedService(FeedRepository repository) {
        this.repository = repository;
    }

    public List<FeedDTO> findAllFeed(String userId) {
//        if(userId != null)

        List<Feed> feedList = repository.findAll();
        return feedList.stream().map(FeedDTO::fromEntity).collect(Collectors.toList());
    }
}