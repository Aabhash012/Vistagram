package com.vistagram.app.api.response;

import com.vistagram.app.domain.PostDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter
@Builder
@Getter
public class PostDetailResponse {

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

    public static PostDetailResponse fromPostDto(PostDto postDto){
        return PostDetailResponse.builder()
                .id(postDto.getId())
                .username(postDto.getUsername())
                .imageUrl(postDto.getImageUrl())
                .caption(postDto.getCaption())
                .poiName(postDto.getPoiName())
                .poiLocation(postDto.getPoiLocation())
                .createdAt(postDto.getCreatedAt())
                .likeCount(postDto.getLikeCount())
                .shareCount(postDto.getShareCount())
                .likedByCurrentUser((postDto.isLikedByCurrentUser()))
                .build();
    }
}
