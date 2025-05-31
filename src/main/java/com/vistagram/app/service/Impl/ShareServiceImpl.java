package com.vistagram.app.service.Impl;

import com.vistagram.app.mapper.PostMapper;
import com.vistagram.app.repository.ShareRepository;
import com.vistagram.app.repository.entity.Share;
import com.vistagram.app.service.Interface.ShareService;
import com.vistagram.app.domain.PostDto;
import com.vistagram.app.repository.PostRepository;
import com.vistagram.app.repository.UserRepository;
import com.vistagram.app.repository.entity.Post;
import com.vistagram.app.repository.entity.User;
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
import com.vistagram.app.exception.ResourceNotFoundException;
@Service
@Transactional
@RequiredArgsConstructor
public class ShareServiceImpl implements ShareService {
    private final ShareRepository shareRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;

    @Override
    public String sharePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Share share = Share.builder()
                .user(user)
                .post(post)
                .build();

        shareRepository.save(share);

        // Generate a shareable link
        return "https://vistagram.app/posts/" + postId;
    }

    @Override
    public Page<PostDto> getUserSharedPosts(Long userId, int page, int size) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Pageable pageable = PageRequest.of(page, size);
        Page<Long> postIds = shareRepository.findSharedPostIdsByUserId(userId, pageable);

        List<Post> posts = postRepository.findAllById(postIds.getContent());
        posts.sort(Comparator.comparing(Post::getCreatedAt).reversed());
        List<PostDto> postDtos = posts.stream()
                .map(post -> postMapper.mapToDto(post, userId))
                .collect(Collectors.toList());

        return new PageImpl<>(postDtos, pageable, postIds.getTotalElements());
    }
}