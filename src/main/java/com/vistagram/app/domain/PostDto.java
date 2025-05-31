package com.vistagram.app.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDto {
    private Long id;
    private String username;
    private String imageUrl;
    private String caption;
    private String poiName;
    private String poiLocation;
    private LocalDateTime createdAt;
    private int likeCount;
    private int shareCount;
    private boolean likedByCurrentUser;
}
