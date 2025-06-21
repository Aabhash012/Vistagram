package com.vistagram.app.service.Impl;

import com.vistagram.app.domain.UserDto;
import com.vistagram.app.domain.UpdateUserDto;
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
import com.vistagram.app.exception.BadRequestException;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BaseService baseService;

    @Override
    public UserDto getUserProfile(Long userId) {
        User user = baseService.getUserOrThrow(userId);
        return userMapper.mapToDto(user);
    }

    @Override
    public UserDto updateUserProfile(Long userId, UpdateUserDto updateUserDto) {

        if (updateUserDto == null) {
            throw new BadRequestException("Update data cannot be null");
        }
        User user = baseService.getUserOrThrow(userId);
        boolean updated = applyUserUpdates(user, updateUserDto);
        if (!updated) {
            return userMapper.mapToDto(user); // No changes, return as-is
        }
        User updatedUser = userRepository.save(user);
        return userMapper.mapToDto(updatedUser);
    }

    @Override
    public Page<UserDto> searchUsers(String query, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.searchByUsername(query, pageable)
                .map(userMapper::mapToDto);
    }
    private boolean applyUserUpdates(User user, UpdateUserDto dto) {

        boolean updated = false;
        if (dto.getUsername() != null && !dto.getUsername().equals(user.getUsername())) {
            if (userRepository.existsByUsername(dto.getUsername())) {
                throw new BadRequestException("Username is already taken");
            }
            user.setUsername(dto.getUsername());
            updated = true;
        }
        return updated;
    }
}
