package com.clds.bottletalk.feed.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@IdClass(FeedLikeId.class)
public class FeedLike implements Serializable {

    @Id
    @Column(name = "feed_id")
    private Long feedId;
    @Id
    @Column(name = "user_id")
    private String userId;
    private boolean isLiked; //1-참, 0-거짓

    public FeedLike(FeedLikeDTO feedLikeDTO){
        this.feedId = feedLikeDTO.getFeedId();
        this.userId = feedLikeDTO.getUserId();
        this.isLiked = feedLikeDTO.isLiked();
    }

    public void updateIsLiked(boolean isLiked) {
        this.isLiked = isLiked;
    }

    public static FeedLike fromFeedLikeDTO(FeedLikeDTO feedLikeDTO){
        return new FeedLike(feedLikeDTO);
    }

}
