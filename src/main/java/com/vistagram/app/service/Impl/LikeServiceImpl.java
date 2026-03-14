package com.vistagram.app.service.Impl;

import com.vistagram.app.domain.PostDto;
import com.vistagram.app.mapper.PostMapper;
import com.vistagram.app.repository.LikeRepository;
import com.vistagram.app.repository.PostRepository;
import com.vistagram.app.repository.entity.Like;
import com.vistagram.app.repository.entity.Post;
import com.vistagram.app.repository.entity.User;
import com.vistagram.app.service.Interface.LikeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Override
    @Transactional
    public void likePost(Long postId, Long userId) {

        Like like = Like.builder()
                .post(Post.builder().id(postId).build())
                .user(User.builder().id(userId).build())
                .build();

        try {
            likeRepository.save(like);
            postRepository.incrementLikeCount(postId);
        } catch (DataIntegrityViolationException ignored) {
            // already liked
        }
    }

    @Override
    @Transactional
    public void unLikePost(Long postId, Long userId) {

        if (likeRepository.existsByUserIdAndPostId(userId, postId)) {
            likeRepository.deleteByUserIdAndPostId(userId, postId);
            postRepository.decrementLikeCount(postId);
        }
    }

    @Override
    public boolean isPostLikedByUser(Long postId, Long userId) {
        return likeRepository.existsByUserIdAndPostId(userId, postId);
    }

    @Override
    public Page<PostDto> getUserLikedPosts(Long userId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Post> likedPosts =
                postRepository.findLikedPostsByUserId(userId, pageable);

        Set<Long> likedPostIds = likedPosts.stream()
                .map(Post::getId)
                .collect(Collectors.toSet());

        return likedPosts.map(post ->
                postMapper.mapToDto(post, likedPostIds));
    }
}