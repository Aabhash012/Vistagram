package com.vistagram.app.service.Interface;
import com.vistagram.app.domain.PostDto;
import org.springframework.data.domain.Page;

public interface ShareService {

    String sharePost(Long postId, Long userId);
    Page<PostDto> getUserSharedPosts(Long userId, int page, int size);
}
