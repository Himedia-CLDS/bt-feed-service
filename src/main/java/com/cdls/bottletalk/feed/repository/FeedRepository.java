package com.cdls.bottletalk.feed.repository;

import com.cdls.bottletalk.feed.model.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRepository extends JpaRepository<Feed, Long> {

}