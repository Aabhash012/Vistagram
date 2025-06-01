package com.vistagram.app.service;

import com.vistagram.app.domain.UserDto;
import com.vistagram.app.domain.UserUpdateDto;
import com.vistagram.app.exception.BadRequestException;
import com.vistagram.app.exception.ResourceNotFoundException;
import com.vistagram.app.mapper.UserMapper;
import com.vistagram.app.repository.UserRepository;
import com.vistagram.app.repository.entity.User;
import com.vistagram.app.service.Impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import java.util.Collections;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getUserProfile_ShouldReturnUser() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        UserDto result = userService.getUserProfile(1L);

        // Assert
        assertEquals("testuser", result.getUsername());
        verify(userRepository).findById(1L);
    }

    @Test
    void getUserProfile_ShouldThrowWhenNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userService.getUserProfile(1L));
    }

    @Test
    void updateUserProfile_ShouldUpdateUsername() {
        // Arrange
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setUsername("oldname");

        UserUpdateDto updateDto = new UserUpdateDto();
        updateDto.setUsername("newname");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.existsByUsername("newname")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        UserDto result = userService.updateUserProfile(1L, updateDto);

        // Assert
        assertEquals("newname", result.getUsername());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void updateUserProfile_ShouldThrowWhenUsernameTaken() {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setUsername("oldname");

        UserUpdateDto updateDto = new UserUpdateDto();
        updateDto.setUsername("takenname");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.existsByUsername("takenname")).thenReturn(true);

        assertThrows(BadRequestException.class, () -> userService.updateUserProfile(1L, updateDto));
    }

    @Test
    void searchUsers_ShouldReturnMatchingUsers() {
        // Arrange
        User user = new User();
        user.setUsername("testuser");
        Page<User> userPage = new PageImpl<>(Collections.singletonList(user));

        when(userRepository.searchByUsername(anyString(), any())).thenReturn(userPage);

        // Act
        Page<UserDto> result = userService.searchUsers("test", 0, 10);

        // Assert
        assertEquals(1, result.getTotalElements());
        assertEquals("testuser", result.getContent().get(0).getUsername());
    }

    @Test
    void searchUsers_ShouldReturnEmptyWhenNoMatches() {
        when(userRepository.searchByUsername(anyString(), any())).thenReturn(Page.empty());
        Page<UserDto> result = userService.searchUsers("nonexistent", 0, 10);
        assertTrue(result.isEmpty());
    }
}
