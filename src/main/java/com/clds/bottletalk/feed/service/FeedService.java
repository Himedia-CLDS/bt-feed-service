package com.clds.bottletalk.feed.service;

import com.clds.bottletalk.common.AWSCognitoService;
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
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FeedService {

    private FeedRepository repository;
    private FeedLikeRepository likeRepository;
    private final AWSCognitoService awsCognitoService;

    public FeedService(FeedRepository repository, FeedLikeRepository likeRepository,AWSCognitoService awsCognitoService) {
        this.repository = repository;
        this.likeRepository = likeRepository;
        this.awsCognitoService = awsCognitoService;
    }

    public List<FeedDTO> findAllFeed(String userId) {
        List<Feed> feedList;
        if(userId == null){
            feedList = repository.findAll();
        } else {
            feedList = repository.findByUserId(userId);
        }
        System.out.println("list: " +feedList);
        return feedList.stream().map(FeedDTO::fromEntity).collect(Collectors.toList());
    }

    public FeedDTO findFeed(Long id, String userId) {
        Feed feed = repository.findByIdAndUserId(id, userId);
        return FeedDTO.fromEntity(feed);
    }

    public String insertFeed(FeedDTO feedDTO) {

        Map<String, String> userInfo = awsCognitoService.getUserInfoFromCognito(feedDTO.getUserId());
        String userEmail = userInfo.get("email");
        feedDTO.setUserEmail(userEmail);
        System.out.println(feedDTO);
        if(userEmail != null){
            repository.save(Feed.fromDTO(feedDTO));
            return userEmail;
        } else {
            return null;
        }
    }

    @Transactional
    public LocalDateTime updateFeed(FeedDTO feedDTO) {
        Feed feed = repository.findByIdAndUserId(feedDTO.getId(), feedDTO.getUserId());
        feed.updateFeed(feedDTO.getContent(), feedDTO.getOrgImgName(), feedDTO.getReImgName(), LocalDateTime.now());
        repository.save(feed);
        return feed.getCreatedAt();
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