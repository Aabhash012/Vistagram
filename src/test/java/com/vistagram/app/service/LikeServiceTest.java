package com.vistagram.app.service;


import com.vistagram.app.repository.LikeRepository;
import com.vistagram.app.repository.PostRepository;
import com.vistagram.app.repository.UserRepository;
import com.vistagram.app.repository.entity.Like;
import com.vistagram.app.repository.entity.Post;
import com.vistagram.app.repository.entity.User;
import com.vistagram.app.service.Impl.LikeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LikeServiceTest {

    @Mock private LikeRepository likeRepository;
    @Mock private PostRepository postRepository;
    @Mock private UserRepository userRepository;
    @InjectMocks private LikeServiceImpl likeService;

    @Test
    void likePost_ShouldCreateLike() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(new Post()));
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
        when(likeRepository.existsByUserAndPost(any(), any())).thenReturn(false);

        likeService.likePost(1L, 1L);
        verify(likeRepository).save(any());
    }

    @Test
    void unlikePost_ShouldDeleteLike() {
        Like like = new Like();
        when(postRepository.findById(1L)).thenReturn(Optional.of(new Post()));
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
        when(likeRepository.findByUserAndPost(any(), any())).thenReturn(Optional.of(like));

        likeService.unlikePost(1L, 1L);
        verify(likeRepository).delete(like);
    }
}
