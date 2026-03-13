package com.vistagram.app.service;

import com.vistagram.app.domain.PostDto;
import com.vistagram.app.mapper.PostMapper;
import com.vistagram.app.exception.ResourceNotFoundException;
import com.vistagram.app.repository.LikeRepository;
import com.vistagram.app.repository.PostRepository;
import com.vistagram.app.repository.ShareRepository;
import com.vistagram.app.repository.UserRepository;
import com.vistagram.app.repository.entity.Post;
import com.vistagram.app.repository.entity.Share;
import com.vistagram.app.repository.entity.User;
import com.vistagram.app.service.Impl.ShareServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShareServiceTest {

    @Mock
    private ShareRepository shareRepository;

    @Mock
    private PostRepository postRepository;
    @Mock
    private LikeRepository likeRepository;

    @Mock
    private PostMapper postMapper;

    @InjectMocks
    private ShareServiceImpl shareService;

    @Test
    void sharePost_ShouldCreateNewShare_WhenShareDoesNotExist() {

        when(shareRepository.incrementShareCount(1L, 1L)).thenReturn(0);
        when(shareRepository.save(any(Share.class))).thenReturn(new Share());

        String result = shareService.sharePost(1L, 1L);

        assertEquals("https://vistagram.app/posts/1", result);

        verify(shareRepository).incrementShareCount(1L, 1L);
        verify(shareRepository).save(any(Share.class));
        verify(postRepository).incrementShareCount(1L);
    }

    @Test
    void sharePost_ShouldIncrementExistingShare() {

        when(shareRepository.incrementShareCount(1L, 1L)).thenReturn(1);

        String result = shareService.sharePost(1L, 1L);

        assertEquals("https://vistagram.app/posts/1", result);

        verify(shareRepository).incrementShareCount(1L, 1L);
        verify(shareRepository, never()).save(any());
        verify(postRepository).incrementShareCount(1L);
    }

    @Test
    void getUserSharedPosts_ShouldReturnPosts() {

        Post post = new Post();
        post.setId(1L);

        Page<Post> postPage = new PageImpl<>(List.of(post));

        when(shareRepository.findSharedPostsByUserId(anyLong(), any()))
                .thenReturn(postPage);

        when(likeRepository.findPostIdsLikedByUser(anyLong()))
                .thenReturn(Set.of(1L));

        when(postMapper.mapToDto(any(Post.class), anySet()))
                .thenReturn(PostDto.builder().id(1L).build());

        Page<PostDto> result = shareService.getUserSharedPosts(1L, 0, 10);

        assertEquals(1, result.getTotalElements());

        verify(shareRepository).findSharedPostsByUserId(anyLong(), any());
        verify(likeRepository).findPostIdsLikedByUser(anyLong());
    }

    @Test
    void getUserSharedPosts_ShouldReturnEmpty_WhenNoPosts() {

        when(shareRepository.findSharedPostsByUserId(anyLong(), any()))
                .thenReturn(Page.empty());

        Page<PostDto> result = shareService.getUserSharedPosts(1L, 0, 10);

        assertTrue(result.isEmpty());
    }
}