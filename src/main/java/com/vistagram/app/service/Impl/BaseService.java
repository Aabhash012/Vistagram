package com.vistagram.app.service.Impl;

import com.vistagram.app.exception.ResourceNotFoundException;
import com.vistagram.app.repository.PostRepository;
import com.vistagram.app.repository.UserRepository;
import com.vistagram.app.repository.LikeRepository;
import com.vistagram.app.repository.entity.Like;
import com.vistagram.app.repository.entity.Post;
import com.vistagram.app.repository.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BaseService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    protected User getUserOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }

    protected Post getPostOrThrow(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
    }
    protected Like getLikeOrThrow(User user, Post post) {
        return likeRepository.findByUserAndPost(user, post)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Like", "user and post", user.getId() + " " + post.getId()));
    }
}
