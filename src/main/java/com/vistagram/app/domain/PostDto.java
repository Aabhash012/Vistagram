package com.vistagram.app.domain;

import com.vistagram.app.repository.entity.Post;
import lombok.*;

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

    private PostDto mapToDto(Post post, Long currentUserId) {
        boolean likedByCurrentUser = post.getLikes().stream()
                .anyMatch(like -> like.getUser().getId().equals(currentUserId));

        return PostDto.builder()
                .id(post.getId())
                .username(post.getUser().getUsername())
                .imageUrl(post.getImageUrl())
                .caption(post.getCaption())
                .poiName(post.getPoiName())
                .poiLocation(post.getPoiLocation())
                .createdAt(post.getCreatedAt())
                .likeCount(post.getLikes().size())
                .shareCount(post.getShares().size())
                .likedByCurrentUser(likedByCurrentUser)
                .build();
    }
}
