package com.vistagram.app.service;


import com.vistagram.app.repository.LikeRepository;
import com.vistagram.app.repository.PostRepository;
import com.vistagram.app.repository.entity.Like;
import com.vistagram.app.service.Impl.LikeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LikeServiceTest {

    @Mock private LikeRepository likeRepository;
    @Mock private PostRepository postRepository;
    @InjectMocks private LikeServiceImpl likeService;

    @Test
    void likePost_ShouldCreateLike() {

        when(likeRepository.existsByUserIdAndPostId(1L, 1L)).thenReturn(false);
        likeService.likePost(1L, 1L);
        verify(likeRepository).save(any(Like.class));
        verify(postRepository).incrementLikeCount(1L);
    }

    @Test
    void unlikePost_ShouldDeleteLike() {

        when(likeRepository.existsByUserIdAndPostId(1L, 1L)).thenReturn(true);
        likeService.unLikePost(1L, 1L);
        verify(likeRepository).deleteByUserIdAndPostId(1L, 1L);
        verify(postRepository).decrementLikeCount(1L);
    }
    @Test
    void likePost_ShouldNotCreateDuplicateLike() {

        when(likeRepository.existsByUserIdAndPostId(1L, 1L)).thenReturn(true);
        likeService.likePost(1L, 1L);
        verify(likeRepository, never()).save(any());
        verify(postRepository, never()).incrementLikeCount(any());
    }
}
