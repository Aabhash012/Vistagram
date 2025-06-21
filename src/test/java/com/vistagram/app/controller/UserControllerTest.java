package com.vistagram.app.controller;

import com.vistagram.app.api.shell.UserController;
import com.vistagram.app.domain.UserDto;
import com.vistagram.app.domain.UpdateUserDto;
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

class UserControllerTest extends ControllerTestBase {

    @InjectMocks
    private UserController userController;

    @Override
    protected Object getController() {
        return userController;
    }

    @Test
    void getUserProfile_ShouldReturnUser() throws Exception {
        UserDto userDto = UserDto.builder()
                .id(1L)
                .username("testuser")
                .build();

        given(userService.getUserProfile(1L)).willReturn(userDto);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    void updateUserProfile_ShouldReturnUpdatedUser() throws Exception {
        UpdateUserDto updateDto = new UpdateUserDto();
        updateDto.setUsername("newusername");

        UserDto userDto = UserDto.builder()
                .id(1L)
                .username("newusername")
                .build();

        given(userService.updateUserProfile(any(UpdateUserDto.class)))
                .willReturn(userDto);

        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"newusername\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("newusername"));
    }

    @Test
    void searchUsers_ShouldReturnMatchingUsers() throws Exception {
        UserDto userDto = UserDto.builder()
                .id(1L)
                .username("testuser")
                .build();

        Page<UserDto> page = new PageImpl<>(List.of(userDto));
        given(userService.searchUsers(anyString(), anyInt(), anyInt())).willReturn(page);

        mockMvc.perform(get("/api/users/search")
                        .param("query", "test")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].username").value("testuser"));
    }
}