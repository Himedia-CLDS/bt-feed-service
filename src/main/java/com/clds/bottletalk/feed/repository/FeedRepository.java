package com.clds.bottletalk.feed.repository;

import com.clds.bottletalk.feed.model.Feed;
import com.clds.bottletalk.feed.model.FeedDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Long> {
    Feed findByIdAndUserId(Long id, String userId);

    @Query(value = "SELECT f.created_at, f.id, f.user_id, f.content, f.org_img_name, f.re_img_name, f.user_email,  " +
            " f.deleted_at, f.updated_at, " +
//            " COALESCE(COUNT(NULLIF(l.is_liked, 0)), 0) AS like_count," + //추후 추가예정
            " COALESCE(NULLIF(l.is_liked, 0),l.is_liked, 0) AS is_liked " +
            " FROM feed f LEFT JOIN feed_like l " +
            " ON f.id = l.feed_id " +
            " WHERE l.user_id = :user_id AND l.is_liked = 1 "+
            " AND f.deleted_at IS NULL " +
            " GROUP BY f.id" +
            " ORDER BY f.created_at DESC",
            nativeQuery = true)
    List<Feed> findAllByIdAndIsLiked(@Param("user_id") String userId);

    @Query(value = "SELECT f.created_at, f.id, f.user_id, f.content, f.org_img_name, f.re_img_name, f.user_email,  " +
            " f.deleted_at, f.updated_at, " +
//            " COALESCE(COUNT(NULLIF(l.is_liked, 0)), 0) AS like_count, " + //추후 추가예정
            " COALESCE(NULLIF(l.is_liked, 0),l.is_liked, 0) AS is_liked " +
            " FROM feed f LEFT JOIN feed_like l " +
            " ON f.id = l.feed_id " +
            " WHERE f.deleted_at IS NULL " +
            " AND f.user_id = :user_id" +
            " GROUP BY f.id" +
            " ORDER BY f.created_at DESC",
            nativeQuery = true)
    List<Feed> findFeedsByUserId(@Param("user_id") String userId);


    @Query(value = "SELECT f.created_at, f.id, f.user_id, f.content, f.org_img_name, f.re_img_name, f.user_email, " +
            " f.deleted_at, updated_at, " +
//            " COALESCE(COUNT(NULLIF(l.is_liked, 0)), 0) AS like_count," + //추후 추가예정
            " COALESCE(NULLIF(l.is_liked, 0),l.is_liked, 0) AS is_liked " +
            " FROM feed f LEFT JOIN feed_like l " +
            " ON f.id = l.feed_id " +
            " WHERE f.deleted_at IS NULL " +
            " GROUP BY f.id" +
            " ORDER BY f.created_at DESC",
            nativeQuery = true)
    List<Feed> findFeedsAll();
}
