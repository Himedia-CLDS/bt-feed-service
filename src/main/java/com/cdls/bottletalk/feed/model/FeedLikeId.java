package com.cdls.bottletalk.feed.model;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedLikeId implements Serializable {
    @Column(name = "feed_id")
    private Long feedId;
    @Column(name = "user_id")
    private String userId;
}
