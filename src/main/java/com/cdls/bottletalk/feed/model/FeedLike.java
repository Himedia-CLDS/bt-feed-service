package com.cdls.bottletalk.feed.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Builder
@Data
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

}
