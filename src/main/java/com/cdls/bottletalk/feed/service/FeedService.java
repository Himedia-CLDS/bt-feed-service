package com.cdls.bottletalk.feed.service;

import com.cdls.bottletalk.feed.model.Feed;
import com.cdls.bottletalk.feed.model.FeedDTO;
import com.cdls.bottletalk.feed.model.FeedLike;
import com.cdls.bottletalk.feed.repository.FeedLikeRepository;
import com.cdls.bottletalk.feed.repository.FeedRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedService {

    private FeedRepository repository;
    private FeedLikeRepository likeRepository;

    public FeedService(FeedRepository repository, FeedLikeRepository likeRepository) {
        this.repository = repository;
        this.likeRepository = likeRepository;
    }

    public List<FeedDTO> findAllFeed(String userId) {
//        if(userId != null)

        List<Feed> feedList = repository.findAll();
        return feedList.stream().map(FeedDTO::fromEntity).collect(Collectors.toList());
    }

    @Transactional
    public FeedLike likeFeed(FeedLike likeDto) {
        FeedLike tempDto = likeRepository.findByFeedIdAndUserId(likeDto.getFeedId(), likeDto.getUserId());
        if(tempDto.isLiked()){
            tempDto.setLiked(false);
        } else {
            tempDto.setLiked(true);
        }
        likeRepository.save(tempDto);
        return tempDto;
    }
}