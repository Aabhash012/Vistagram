package com.vistagram.app.service.Interface;
import com.vistagram.app.domain.PostDto;
import org.springframework.data.domain.Page;

public interface LikeService {

    void likePost(Long postId, Long userId);
    void unlikePost(Long postId, Long userId);
    boolean isPostLikedByUser(Long postId, Long userId);
    Page<PostDto> getUserLikedPosts(Long userId, int page, int size);
}
