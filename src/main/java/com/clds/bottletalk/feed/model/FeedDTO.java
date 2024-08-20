package com.clds.bottletalk.feed.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FeedDTO {

    private Long id;
    private String userId;
    private String userEmail;
    private String content;
    private String orgImgName;
    private String reImgName;
    private LocalDateTime createdAt;

    private Long likeCount;

    public FeedDTO(){

    }

    public FeedDTO(Feed feed){
        this.id = feed.getId();
        this.userId = feed.getUserId();
        this.userEmail = feed.getUserEmail();
        this.content = feed.getContent();
        this.orgImgName = feed.getOrgImgName();
        this.reImgName = feed.getReImgName();
        this.createdAt = feed.getCreatedAt();
        this.likeCount = feed.getLikeCount();
    }

    public static FeedDTO fromEntity(Feed feed){
        return new FeedDTO(feed);
    }
}