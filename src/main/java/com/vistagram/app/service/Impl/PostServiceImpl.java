package com.vistagram.app.service.Impl;

import com.vistagram.app.domain.PostDto;
import com.vistagram.app.exception.BadRequestException;
import com.vistagram.app.exception.NotFoundException;
import com.vistagram.app.mapper.PostMapper;
import com.vistagram.app.repository.LikeRepository;
import com.vistagram.app.repository.PostRepository;
import com.vistagram.app.repository.UserRepository;
import com.vistagram.app.repository.entity.Post;
import com.vistagram.app.repository.entity.User;
import com.vistagram.app.service.Interface.PostService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.vistagram.app.exception.UnauthorizedException;

import java.util.Collections;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FileStorageServiceImpl fileStorageService;
    private final BaseService baseService;
    private final PostMapper postMapper;
    private final LikeRepository likeRepository;

    @Override
    @Transactional
    public PostDto createPost(MultipartFile image,
                              String caption,
                              String poiName,
                              String poiLocation,
                              Long userId) {

        validateImageFile(image);

        String imageUrl = fileStorageService.storeFile(image);

        Post post = Post.builder()
                .user(User.builder().id(userId).build())
                .imageUrl(imageUrl)
                .caption(caption)
                .poiName(poiName)
                .poiLocation(poiLocation)
                .build();

        Post savedPost = postRepository.save(post);

        return postMapper.mapToDto(savedPost, Collections.emptySet());
    }

    @Override
    public Page<PostDto> getTimeline(int page, int size, Long currentUserId) {

        Pageable pageable = PageRequest.of(page, size);
        Set<Long> likedPostIds = Collections.emptySet();

        if (currentUserId != null) {
            likedPostIds = likeRepository.findPostIdsLikedByUser(currentUserId);
        }

        Set<Long> finalLikedPostIds = likedPostIds;

        return postRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(post -> postMapper.mapToDto(post, finalLikedPostIds));
    }

    @Override
    public PostDto getPostById(Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post not found"));

        return postMapper.mapToDto(post, Collections.emptySet());
    }

    @Override
    public Page<PostDto> getUserPosts(Long userId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Set<Long> likedPostIds = likeRepository.findPostIdsLikedByUser(userId);

        return postRepository
                .findByUserIdOrderByCreatedAtDesc(userId, pageable)
                .map(post -> postMapper.mapToDto(post, likedPostIds));
    }

    @Override
    public Page<PostDto> searchPosts(String query, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return postRepository
                .searchPosts(query, pageable)
                .map(post -> postMapper.mapToDto(post, Collections.emptySet()));
    }

    @Override
    @Transactional
    public void deletePost(Long postId, Long userId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post not found"));

        if (!post.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("You are not authorized to delete this post");
        }

        fileStorageService.deleteFile(post.getImageUrl());

        postRepository.delete(post);
    }

    private void validateImageFile(MultipartFile image) {
        if (image == null || image.isEmpty()) {
            throw new BadRequestException("Image file is required to create a post.");
        }
    }

    private Post buildPost(User user, String imageUrl, String caption, String poiName, String poiLocation) {
        return Post.builder()
                .user(user)
                .imageUrl(imageUrl)
                .caption(caption)
                .poiName(poiName)
                .poiLocation(poiLocation)
                .build();
    }

    private void validatePostOwnership(Post post, Long userId) {
        if (!post.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("You are not authorized to delete this post");
        }
    }
}
