package com.vistagram.app.service.Impl;

import com.vistagram.app.mapper.PostMapper;
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
import static com.vistagram.app.utils.Constants.IMG_URL;

@Service
@Transactional
@RequiredArgsConstructor
public class ShareServiceImpl implements ShareService {

    private final ShareRepository shareRepository;
    private final PostRepository postRepository;
    private final BaseService baseService;
    private final PostMapper postMapper;

    @Override
    public String sharePost(Long postId, Long userId) {

        Post post = baseService.getPostOrThrow(postId);
        User user = baseService.getUserOrThrow(userId);
        Optional<Share> alreadyShared = shareRepository.findByUserAndPost(user, post);
        if(alreadyShared.isPresent()){
            Share existingShare = alreadyShared.get();
            existingShare.incrementShareCount(); // Custom method in Share entity
            shareRepository.save(existingShare);
        }
        else{
            Share share = buildShare(user, post);
            post.addShare(share);
            //shareRepository.save(share); can be removed as this is also done by next line as we use cascade in entity
            postRepository.save(post);
        }
        return generateShareLink(postId);
    }

    @Override
    public Page<PostDto> getUserSharedPosts(Long userId, int page, int size) {

        User user = baseService.getUserOrThrow(userId);
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> sharedPosts = shareRepository.findSharedPostsByUserId(userId, pageable);
        return sharedPosts.map(post -> postMapper.mapToDto(post, userId));
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