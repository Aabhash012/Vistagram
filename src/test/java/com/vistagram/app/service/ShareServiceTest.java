package com.vistagram.app.service;

import com.vistagram.app.domain.PostDto;
import com.vistagram.app.mapper.PostMapper;
import com.vistagram.app.exception.ResourceNotFoundException;
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
    private UserRepository userRepository;

    @Mock
    private PostMapper postMapper;

    @InjectMocks
    private ShareServiceImpl shareService;

    @Test
    void sharePost_ShouldReturnShareLink() {
        // Arrange
        Post post = new Post();
        post.setId(1L);
        User user = new User();
        user.setId(1L);

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(shareRepository.save(any(Share.class))).thenReturn(new Share());

        // Act
        String result = shareService.sharePost(1L, 1L);

        // Assert
        assertEquals("https://vistagram.app/posts/1", result);
        verify(shareRepository).save(any(Share.class));
    }

    @Test
    void sharePost_ShouldThrowWhenPostNotFound() {
        when(postRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> shareService.sharePost(1L, 1L));
    }

    @Test
    void sharePost_ShouldThrowWhenUserNotFound() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(new Post()));
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> shareService.sharePost(1L, 1L));
    }

    @Test
    void getUserSharedPosts_ShouldReturnPosts() {
        // Arrange
        Page<Long> postIdPage = new PageImpl<>(Collections.singletonList(1L));
        Post post = new Post(); post.setId(1L);
        when(shareRepository.findSharedPostIdsByUserId(anyLong(), any())).thenReturn(postIdPage);
        when(postRepository.findAllById(any())).thenReturn(List.of(post));
        when(postMapper.mapToDto(any(Post.class), anyLong())).thenReturn(PostDto.builder().id(1L).build());

        // Act
        Page<PostDto> result = shareService.getUserSharedPosts(1L, 0, 10);

        // Assert
        assertEquals(1, result.getTotalElements());
        verify(shareRepository).findSharedPostIdsByUserId(anyLong(), any());
    }


    @Test
    void getUserSharedPosts_ShouldReturnEmptyWhenNoShares() {
        when(shareRepository.findSharedPostIdsByUserId(anyLong(), any())).thenReturn(Page.empty());
        Page<PostDto> result = shareService.getUserSharedPosts(1L, 0, 10);
        assertTrue(result.isEmpty());
    }
}
