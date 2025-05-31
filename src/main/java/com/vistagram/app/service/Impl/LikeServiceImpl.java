package com.vistagram.app.service.Impl;

import com.vistagram.app.domain.PostDto;
import com.vistagram.app.repository.LikeRepository;
import com.vistagram.app.repository.PostRepository;
import com.vistagram.app.repository.UserRepository;
import com.vistagram.app.repository.entity.Like;
import com.vistagram.app.repository.entity.Post;
import com.vistagram.app.repository.entity.User;
import com.vistagram.app.service.Interface.LikeService;
import com.vistagram.app.service.Interface.PostService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
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
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public void likePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        if (likeRepository.existsByUserAndPost(user, post)) {
            throw new BadRequestException("You have already liked this post");
        }

        Like like = Like.builder()
                .user(user)
                .post(post)
                .build();

        likeRepository.save(like);
    }

    @Override
    public void unlikePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Like like = likeRepository.findByUserAndPost(user, post)
                .orElseThrow(() -> new ResourceNotFoundException("Like", "user and post", userId + " " + postId));

        likeRepository.delete(like);
    }

    @Override
    public boolean isPostLikedByUser(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        return likeRepository.existsByUserAndPost(user, post);
    }

    @Override
    public Page<PostDto> getUserLikedPosts(Long userId, int page, int size) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Pageable pageable = PageRequest.of(page, size);
        Page<Long> postIds = likeRepository.findLikedPostIdsByUserId(userId, pageable);

        List<Post> posts = postRepository.findAllById(postIds.getContent());
        posts.sort(Comparator.comparing(Post::getCreatedAt).reversed());

        return new PageImpl<>(
                posts.stream()
                        .map(post -> {
                            PostDto dto = modelMapper.map(post, PostDto.class);
                            dto.setUsername(post.getUser().getUsername());
                            dto.setLikeCount(post.getLikes().size());
                            dto.setShareCount(post.getShares().size());
                            return dto;
                        })
                        .collect(Collectors.toList()),
                pageable,
                postIds.getTotalElements()
        );
    }
}