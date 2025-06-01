package com.vistagram.app.controller;

import com.vistagram.app.api.shell.PostController;
import com.vistagram.app.domain.PostDto;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import java.time.LocalDateTime;
import java.util.List;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PostControllerTest extends ControllerTestBase {

    @InjectMocks
    private PostController postController;

    @Override
    protected Object getController() {
        return postController;
    }

    @Test
    void createPost_ShouldReturnCreatedPost() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "image", "test.jpg", "image/jpeg", "test image".getBytes());

        PostDto postDto = PostDto.builder()
                .id(1L)
                .username("testuser")
                .caption("Test caption")
                .imageUrl("test.jpg")
                .createdAt(LocalDateTime.now())
                .build();

        given(postService.createPost(any(), anyString(), anyString(), anyString(), anyLong()))
                .willReturn(postDto);

        mockMvc.perform(multipart("/api/posts")
                        .file(file)
                        .param("caption", "Test caption")
                        .param("userId", "1")
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    void getTimeline_ShouldReturnPosts() throws Exception {
        PostDto postDto = PostDto.builder()
                .id(1L)
                .username("user1")
                .caption("Post 1")
                .build();

        Page<PostDto> page = new PageImpl<>(List.of(postDto));
        given(postService.getTimeline(anyInt(), anyInt(), anyLong())).willReturn(page);

        mockMvc.perform(get("/api/posts")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[0].username").value("user1"));
    }

    @Test
    void getPostById_ShouldReturnPost() throws Exception {
        PostDto postDto = PostDto.builder()
                .id(1L)
                .username("user1")
                .caption("Test post")
                .build();

        given(postService.getPostById(1L)).willReturn(postDto);

        mockMvc.perform(get("/api/posts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("user1"));
    }

    @Test
    void deletePost_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/posts/1")
                        .param("userId", "1"))
                .andExpect(status().isNoContent());
    }
}
