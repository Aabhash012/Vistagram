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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final BaseService baseService;
    private final PostMapper postMapper;

    @Override
    public void likePost(Long postId, Long userId) {

        if (!likeRepository.existsByUserIdAndPostId(userId, postId)) {
            Post post = baseService.getPostOrThrow(postId);
            User user = baseService.getUserOrThrow(userId);
            Like like = buildLike(user);
            post.addLike(like);
            likeRepository.save(like);
            postRepository.save(post);
        }
    }

    @Override
    public void unLikePost(Long postId, Long userId) {

        if (likeRepository.existsByUserIdAndPostId(userId, postId)) {
            Post post = baseService.getPostOrThrow(postId);
            User user = baseService.getUserOrThrow(userId);
            Like like = baseService.getLikeOrThrow(user, post);
            post.removeLike(like);
            likeRepository.delete(like);
            postRepository.save(post);
        }
    }

    @Override
    public boolean isPostLikedByUser(Long postId, Long userId) {

        Post post = baseService.getPostOrThrow(postId);
        User user = baseService.getUserOrThrow(userId);
        return likeRepository.existsByUserIdAndPostId(user.getId(), post.getId());
    }

    @Override
    public Page<PostDto> getUserLikedPosts(Long userId, int page, int size) {

        User user = baseService.getUserOrThrow(userId);
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> likedPosts = postRepository.findLikedPostsByUserId(userId, pageable);
        return likedPosts.map(post -> postMapper.mapToDto(post, userId));
    }

    private Like buildLike(User user) {
        return Like.builder()
                .user(user)
                .build();
    }
}