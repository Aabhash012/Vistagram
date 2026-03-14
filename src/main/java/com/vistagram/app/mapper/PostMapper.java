package com.vistagram.app.mapper;

import com.vistagram.app.domain.PostDto;
import com.vistagram.app.repository.entity.Post;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class PostMapper {

    public PostDto mapToDto(Post post, Set<Long> likedPostIds) {

        boolean likedByCurrentUser =
                likedPostIds != null && likedPostIds.contains(post.getId());
        return PostDto.builder()
                .id(post.getId())
                .username(post.getUser().getUsername())
                .imageUrl(post.getImageUrl())
                .caption(post.getCaption())
                .poiName(post.getPoiName())
                .poiLocation(post.getPoiLocation())
                .createdAt(post.getCreatedAt())
                .likeCount(post.getLikeCount())
                .shareCount(post.getShareCount())
                .commentCount(post.getCommentCount())
                .likedByCurrentUser(likedByCurrentUser)
                .build();
    }
}