package com.clds.bottletalk.feed.repository;

import com.clds.bottletalk.feed.model.FeedLike;
import com.clds.bottletalk.feed.model.FeedLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedLikeRepository extends JpaRepository<FeedLike, FeedLikeId> {
    FeedLike findByFeedIdAndUserId(Long feedId, String userId);
}
