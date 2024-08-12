package com.clds.bottletalk.feed.service;

import com.clds.bottletalk.feed.model.Feed;
import com.clds.bottletalk.feed.model.FeedDTO;
import com.clds.bottletalk.feed.model.FeedLike;
import com.clds.bottletalk.feed.repository.FeedLikeRepository;
import com.clds.bottletalk.feed.repository.FeedRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    public FeedDTO findFeed(Long id, String userId) {
        Feed feed = repository.findByIdAndUserId(id, userId);
        return FeedDTO.fromEntity(feed);
    }

    public void insertFeed(FeedDTO feedDTO) {
        Feed feed = Feed.fromDTO(feedDTO);
        repository.save(feed);
    }

    @Transactional
    public FeedDTO updateFeed(FeedDTO feedDTO) {
        Feed feed = repository.findByIdAndUserId(feedDTO.getId(), feedDTO.getUserId());
        feed.updateFeed(feedDTO.getContent(), feedDTO.getOrgImgName(), feedDTO.getReImgName(), LocalDateTime.now());
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