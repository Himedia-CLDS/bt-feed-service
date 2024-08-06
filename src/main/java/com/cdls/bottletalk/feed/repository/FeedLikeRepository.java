package com.cdls.bottletalk.feed.repository;

import com.cdls.bottletalk.feed.model.FeedLike;
import com.cdls.bottletalk.feed.model.FeedLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedLikeRepository extends JpaRepository<FeedLike, FeedLikeId> {
    FeedLike findByFeedIdAndUserId(Long feedId, String userId);
}
