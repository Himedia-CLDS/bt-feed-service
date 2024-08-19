package com.clds.bottletalk.feed.model;

import lombok.Data;

@Data
public class FeedLikeDTO {

    private Long feedId;
    private String userId;
    private boolean isLiked;

    public FeedLikeDTO() {}

    public FeedLikeDTO(FeedLike feedLike) {
        this.feedId = feedLike.getFeedId();
        this.userId = feedLike.getUserId();
        this.isLiked = feedLike.isLiked();
    }

    public static FeedLikeDTO fromFeedLike(FeedLike feedLike) {
        return new FeedLikeDTO(feedLike);
    }
}
