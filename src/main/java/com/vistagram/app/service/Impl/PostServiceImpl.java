package com.vistagram.app.service.Impl;

import com.vistagram.app.domain.PostDto;
import com.vistagram.app.exception.BadRequestException;
import com.vistagram.app.mapper.PostMapper;
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

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FileStorageServiceImpl fileStorageService;
    private final BaseService baseService;
    private final PostMapper postMapper;

    @Override
    public PostDto createPost(MultipartFile image, String caption, String poiName, String poiLocation, Long userId) {

        validateImageFile(image);
        User user = baseService.getUserOrThrow(userId);
        String imageUrl = fileStorageService.storeFile(image);
        Post post = buildPost(user, imageUrl, caption, poiName, poiLocation);

        user.addPost(post);
        userRepository.save(user);
        //Post savedPost = postRepository.save(post); cascading does this
        return postMapper.mapToDto(post, userId);
    }

    @Override
    public Page<PostDto> getTimeline(int page, int size, Long currentUserId) {

        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(post -> postMapper.mapToDto(post, currentUserId));
    }

    @Override
    public PostDto getPostById(Long postId) {

        Post post = baseService.getPostOrThrow(postId);
        return postMapper.mapToDto(post, null);
    }

    @Override
    public Page<PostDto> getUserPosts(Long userId, int page, int size) {

        User user = baseService.getUserOrThrow(userId);
        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findByUserOrderByCreatedAtDesc(user, pageable)
                .map(post -> postMapper.mapToDto(post, userId));
    }

    @Override
    public Page<PostDto> searchPosts(String query, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        return postRepository.searchPosts(query, pageable)
                .map(post -> postMapper.mapToDto(post, null));
    }

    @Override
    public void deletePost(Long postId, Long userId) {

        Post post = baseService.getPostOrThrow(postId);
        validatePostOwnership(post, userId);
        fileStorageService.deleteFile(post.getImageUrl());
        User user = post.getUser();
        user.removePost(post);
        userRepository.save(user);
        //postRepository.delete(post); // 🔁 Post is deleted automatically due to orphanRemoval = true
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
