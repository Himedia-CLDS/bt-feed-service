package com.clds.bottletalk.feed.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String userEmail;
    private String content;
    private String orgImgName;
    private String reImgName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    @Transient
    private boolean isLiked;

    @Transient
    private Long likeCount;

    public Feed(FeedDTO feedDTO) {
        this.id = feedDTO.getId();
        this.userId = feedDTO.getUserId();
        this.userEmail = feedDTO.getUserEmail();
        this.content = feedDTO.getContent();
        this.orgImgName = feedDTO.getOrgImgName();
        this.reImgName = feedDTO.getReImgName();
        this.createdAt = feedDTO.getCreatedAt();
        this.likeCount = feedDTO.getLikeCount();
    }

    public void updateFeed(String content, String orgImgName, String reImgName, LocalDateTime updatedAt){
        this.content = content;
        this.orgImgName = orgImgName;
        this.reImgName = reImgName;
        this.updatedAt = updatedAt;
    }

    public void deleteFeed(LocalDateTime deletedAt){
        this.deletedAt = deletedAt;
    }

    public static Feed fromDTO(FeedDTO feedDTO) {
        return new Feed(feedDTO);
    }

}
