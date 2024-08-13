package com.clds.bottletalk.feed.repository;

import com.clds.bottletalk.feed.model.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Long> {
    Feed findByIdAndUserId(Long id, String userId);

    @Query(value = "select f.created_at, f.id, f.content, f.re_img_name, f.user_email, f.deleted_at, f.updated_at from feed f, feed_like l " +
            "where f.id = l.feed_id and l.user_id = :user_id and l.is_liked = 1",
            nativeQuery = true)
    List<Feed> findAllByIdAndIsLiked(@Param("user_id") String userId);

    List<Feed> findByUserId(String userId);
}