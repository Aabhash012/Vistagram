package com.vistagram.app.controller;

import com.vistagram.app.api.shell.LikeController;
import com.vistagram.app.domain.PostDto;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import java.util.List;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class LikeControllerTest extends ControllerTestBase {

    @InjectMocks
    private LikeController likeController;

    @Override
    protected Object getController() {
        return likeController;
    }

    @Test
    void likePost_ShouldReturnOk() throws Exception {
        mockMvc.perform(post("/api/likes/1")
                        .param("userId", "1"))
                .andExpect(status().isOk());

        verify(likeService).likePost(1L, 1L);
    }

    @Test
    void unlikePost_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/likes/1")
                        .param("userId", "1"))
                .andExpect(status().isNoContent());

        verify(likeService).unlikePost(1L, 1L);
    }

    @Test
    void isPostLikedByUser_ShouldReturnStatus() throws Exception {
        given(likeService.isPostLikedByUser(1L, 1L)).willReturn(true);

        mockMvc.perform(get("/api/likes/1/status")
                        .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void getUserLikedPosts_ShouldReturnPosts() throws Exception {
        PostDto postDto = PostDto.builder().id(1L).username("user1").build();
        Page<PostDto> page = new PageImpl<>(List.of(postDto));

        given(likeService.getUserLikedPosts(anyLong(), anyInt(), anyInt())).willReturn(page);

        mockMvc.perform(get("/api/likes/user/1")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L));
    }
}
