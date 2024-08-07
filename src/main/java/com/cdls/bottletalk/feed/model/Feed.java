package com.cdls.bottletalk.feed.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String content;
    private String img;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public Feed(FeedDTO feedDTO) {
        this.id = feedDTO.getId();
        this.userId = feedDTO.getUserId();
        this.content = feedDTO.getContent();
        this.img = feedDTO.getImg();
        this.createdAt = feedDTO.getCreatedAt();
    }

    public void updateFeed(String content, String img, LocalDateTime updatedAt){
        this.content = content;
        this.img = img;
        this.updatedAt = updatedAt;
    }

    public void deleteFeed(LocalDateTime deletedAt){
        this.deletedAt = deletedAt;
    }

    public static Feed fromDTO(FeedDTO feedDTO) {
        return new Feed(feedDTO);
    }

    @Override
    public String toString() {
        return "Feed{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", content='" + content + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
