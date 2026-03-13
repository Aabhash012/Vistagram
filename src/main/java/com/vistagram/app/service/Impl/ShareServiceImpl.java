package com.vistagram.app.service.Impl;

import com.vistagram.app.mapper.PostMapper;
import com.vistagram.app.repository.LikeRepository;
import com.vistagram.app.repository.ShareRepository;
import com.vistagram.app.repository.entity.Share;
import com.vistagram.app.service.Interface.ShareService;
import com.vistagram.app.domain.PostDto;
import com.vistagram.app.repository.PostRepository;
import com.vistagram.app.repository.entity.Post;
import com.vistagram.app.repository.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Set;

import static com.vistagram.app.utils.Constants.IMG_URL;

@Service
@RequiredArgsConstructor
public class ShareServiceImpl implements ShareService {

    private final ShareRepository shareRepository;
    private final PostRepository postRepository;
    private final BaseService baseService;
    private final PostMapper postMapper;
    private final LikeRepository likeRepository;

    @Override
    @Transactional
    public String sharePost(Long postId, Long userId) {

        int updatedRows = shareRepository.incrementShareCount(userId, postId);
        if (updatedRows == 0) {
            Share share = Share.builder()
                    .user(User.builder().id(userId).build())
                    .post(Post.builder().id(postId).build())
                    .shareCount(1)
                    .build();
            shareRepository.save(share);
        }
        postRepository.incrementShareCount(postId);
        return generateShareLink(postId);
    }

    @Override
    public Page<PostDto> getUserSharedPosts(Long userId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Post> sharedPosts = shareRepository.findSharedPostsByUserId(userId, pageable);
        Set<Long> likedPostIds = likeRepository.findPostIdsLikedByUser(userId);
        return sharedPosts.map(post -> postMapper.mapToDto(post, likedPostIds));
    }
    private Share buildShare(User user, Post post) {
        return Share.builder()
                .user(user)
                .post(post)
                .build();
    }
    private String generateShareLink(Long postId) {
        return IMG_URL + postId;
    }
}