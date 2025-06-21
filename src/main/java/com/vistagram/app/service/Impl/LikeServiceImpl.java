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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
        Page<Long> postIds = likeRepository.findLikedPostIdsByUserId(userId, pageable);
        List<Post> posts = postRepository.findAllById(postIds.getContent());
        posts.sort(Comparator.comparing(Post::getCreatedAt).reversed());
        List<PostDto> postDtos = posts.stream()
                .map(post -> postMapper.mapToDto(post, userId))
                .collect(Collectors.toList());
        return new PageImpl<>(postDtos, pageable, postIds.getTotalElements());
    }

    private Like buildLike(User user) {
        return Like.builder()
                .user(user)
                .build();
    }
}