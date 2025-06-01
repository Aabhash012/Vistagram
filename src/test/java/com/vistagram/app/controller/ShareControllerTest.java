package com.vistagram.app.controller;

import com.vistagram.app.api.shell.ShareController;
import com.vistagram.app.domain.PostDto;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ShareControllerTest extends ControllerTestBase {

    @InjectMocks
    private ShareController shareController;

    @Override
    protected Object getController() {
        return shareController;
    }

    @Test
    void sharePost_ShouldReturnShareLink() throws Exception {
        given(shareService.sharePost(1L, 1L)).willReturn("https://vistagram.app/posts/1");

        mockMvc.perform(post("/api/shares/1")
                        .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("https://vistagram.app/posts/1"));
    }

    @Test
    void getUserSharedPosts_ShouldReturnPosts() throws Exception {
        PostDto postDto = PostDto.builder().id(1L).username("user1").build();
        Page<PostDto> page = new PageImpl<>(List.of(postDto));

        given(shareService.getUserSharedPosts(anyLong(), anyInt(), anyInt())).willReturn(page);

        mockMvc.perform(get("/api/shares/user/1")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L));
    }
}
