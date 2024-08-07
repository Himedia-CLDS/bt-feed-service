package com.cdls.bottletalk.feed.service;

import com.cdls.bottletalk.feed.model.Feed;
import com.cdls.bottletalk.feed.model.FeedDTO;
import com.cdls.bottletalk.feed.model.FeedLike;
import com.cdls.bottletalk.feed.repository.FeedLikeRepository;
import com.cdls.bottletalk.feed.repository.FeedRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public void insertFeed(FeedDTO feedDTO) {
        Feed feed = Feed.fromDTO(feedDTO);
        repository.save(feed);
    }

    @Transactional
    public FeedDTO updateFeed(FeedDTO feedDTO) {
        Feed feed = repository.findByIdAndUserId(feedDTO.getId(), feedDTO.getUserId());
        feed.updateFeed(feedDTO.getContent(), feedDTO.getImg(), LocalDateTime.now());
        repository.save(feed);
        return new FeedDTO(feed);
    }

    public FeedDTO deleteFeed(FeedDTO feedDTO) {
        Feed feed = repository.findByIdAndUserId(feedDTO.getId(), feedDTO.getUserId());
        feed.deleteFeed(LocalDateTime.now());
        repository.save(feed);
        return new FeedDTO(feed);
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

    public List<FeedDTO> findLikedFeed(String userId) {
        List<Feed> likeList = repository.findAllByIdAndIsLiked(userId);
        return likeList.stream().map(FeedDTO::fromEntity).collect(Collectors.toList());
    }
}