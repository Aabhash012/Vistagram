package com.vistagram.app.service.Impl;

import com.vistagram.app.domain.UserDto;
import com.vistagram.app.domain.UserUpdateDto;
import com.vistagram.app.mapper.UserMapper;
import com.vistagram.app.service.Interface.UserService;
import com.vistagram.app.repository.UserRepository;
import com.vistagram.app.repository.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.vistagram.app.exception.ResourceNotFoundException;
import com.vistagram.app.exception.BadRequestException;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Override
    public UserDto getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return userMapper.mapToDto(user);
    }

    @Override
    public UserDto updateUserProfile(Long userId, UserUpdateDto userUpdateDto) {
        User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        if (userUpdateDto.getUsername() != null &&
                    !userUpdateDto.getUsername().equals(user.getUsername())) {
            if (userRepository.existsByUsername(userUpdateDto.getUsername())) {
                    throw new BadRequestException("Username is already taken");
            }
                user.setUsername(userUpdateDto.getUsername());
        }
            // Add other fields to update as needed
        User updatedUser = userRepository.save(user);
        return userMapper.mapToDto(updatedUser);
    }

    @Override
    public Page<UserDto> searchUsers(String query, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.searchByUsername(query, pageable)
                .map(userMapper::mapToDto);
    }
}
