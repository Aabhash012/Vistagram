package com.vistagram.app.service.Impl;
import com.vistagram.app.domain.PostDto;

import com.vistagram.app.repository.PostRepository;
import com.vistagram.app.repository.UserRepository;
import com.vistagram.app.repository.entity.Post;
import com.vistagram.app.repository.entity.User;
import com.vistagram.app.service.Interface.PostService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;
    private final ModelMapper modelMapper;

    @Override
    public PostDto createPost(MultipartFile image, String caption, String poiName, String poiLocation, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        String imageUrl = fileStorageService.storeFile(image);

        Post post = Post.builder()
                .user(user)
                .imageUrl(imageUrl)
                .caption(caption)
                .poiName(poiName)
                .poiLocation(poiLocation)
                .build();

        Post savedPost = postRepository.save(post);
        return mapToDto(savedPost);
    }

    @Override
    public Page<PostDto> getTimeline(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return postRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(this::mapToDto);
    }

    @Override
    public PostDto getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        return mapToDto(post);
    }

    @Override
    public Page<PostDto> getUserPosts(Long userId, int page, int size) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return postRepository.findByUser(user, pageable)
                .map(this::mapToDto);
    }

    @Override
    public Page<PostDto> searchPosts(String query, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return postRepository.searchPosts(query, pageable)
                .map(this::mapToDto);
    }

    @Override
    public void deletePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        if (!post.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("You are not authorized to delete this post");
        }

        fileStorageService.deleteFile(post.getImageUrl());
        postRepository.delete(post);
    }

    private PostDto mapToDto(Post post) {
        PostDto postDto = modelMapper.map(post, PostDto.class);
        postDto.setUsername(post.getUser().getUsername());
        postDto.setLikeCount(post.getLikes().size());
        postDto.setShareCount(post.getShares().size());
        return postDto;
    }
}
