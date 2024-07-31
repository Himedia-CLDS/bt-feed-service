package com.cdls.bottletalk.feed.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FeedDTO {

    private Long id;
    private String userId;
    private String content;
    private String img;
    private LocalDateTime createdAt;

    public FeedDTO(){

    }

    public FeedDTO(Feed feed){
        this.id = feed.getId();
        this.userId = feed.getUserId();
        this.content = feed.getContent();
        this.img = feed.getImg();
        this.createdAt = feed.getCreatedAt();
    }

    public static FeedDTO fromEntity(Feed feed){
        return new FeedDTO(feed);
    }
}