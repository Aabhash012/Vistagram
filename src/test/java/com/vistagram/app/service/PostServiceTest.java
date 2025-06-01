package com.vistagram.app.service;

import com.vistagram.app.domain.PostDto;
import com.vistagram.app.exception.ResourceNotFoundException;
import com.vistagram.app.repository.PostRepository;
import com.vistagram.app.repository.entity.Post;
import com.vistagram.app.service.Impl.PostServiceImpl;
import com.vistagram.app.service.Interface.FileStorageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;
    @Mock private FileStorageService fileStorageService;
    @InjectMocks
    private PostServiceImpl postService;

    @Test
    void createPost_ShouldSaveAndReturnPost() {
        MultipartFile file = mock(MultipartFile.class);
        when(fileStorageService.storeFile(any())).thenReturn("test.jpg");

        Post savedPost = new Post();
        savedPost.setId(1L);
        when(postRepository.save(any())).thenReturn(savedPost);

        PostDto result = postService.createPost(file, "caption", null, null, 1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void getPostById_ShouldReturnPostWhenExists() {
        Post post = new Post();
        post.setId(1L);
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        PostDto result = postService.getPostById(1L);
        assertEquals(1L, result.getId());
    }

    @Test
    void getPostById_ShouldThrowWhenNotFound() {
        when(postRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> postService.getPostById(1L));
    }
}
