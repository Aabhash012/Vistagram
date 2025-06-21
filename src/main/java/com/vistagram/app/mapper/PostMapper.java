package com.vistagram.app.mapper;

import com.vistagram.app.domain.PostDto;
import com.vistagram.app.repository.entity.Post;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostMapper {

    private final ModelMapper modelMapper;
    public PostDto mapToDto(Post post, Long currentUserId) {
        PostDto postDto = modelMapper.map(post, PostDto.class);
        // Manually set values that ModelMapper cannot auto-map
        postDto.setUsername(post.getUser().getUsername());
        postDto.setLikeCount(post.getLikeCount());
        postDto.setShareCount(post.getShareCount());
        boolean likedByCurrentUser = false;
        if (currentUserId != null) {
            likedByCurrentUser = post.getLikes().stream()
                    .anyMatch(like -> like.getUser().getId().equals(currentUserId));
        }
        postDto.setLikedByCurrentUser(likedByCurrentUser);
        return postDto;
    }
}
