package com.vistagram.app.service.Impl;

import com.vistagram.app.domain.UserDto;
import com.vistagram.app.domain.UpdateUserDto;
import com.vistagram.app.exception.NotFoundException;
import com.vistagram.app.mapper.UserMapper;
import com.vistagram.app.service.Interface.UserService;
import com.vistagram.app.repository.UserRepository;
import com.vistagram.app.repository.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.vistagram.app.exception.BadRequestException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto getUserProfile(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return userMapper.mapToDto(user);
    }

    @Override
    @Transactional
    public UserDto updateUserProfile(UpdateUserDto updateUserDto) {

        validateUpdateRequest(updateUserDto);
        User user = userRepository.findById(updateUserDto.getUserID())
                .orElseThrow(() -> new NotFoundException("User not found"));
        if (updateUserDto.getUsername() != null &&
                !updateUserDto.getUsername().equals(user.getUsername())) {
            user.setUsername(updateUserDto.getUsername());
        }
        try {
            User savedUser = userRepository.save(user);
            return userMapper.mapToDto(savedUser);
        } catch (DataIntegrityViolationException ex) {
            throw new BadRequestException("Username already taken");
        }
    }

    @Override
    public Page<UserDto> searchUsers(String query, int page, int size) {

        String sanitizedQuery = sanitizeQuery(query);
        if (sanitizedQuery.isBlank()) {
            return Page.empty();
        }
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.searchByUsername(sanitizedQuery, pageable)
                .map(userMapper::mapToDto);
    }
    private void validateUpdateRequest(UpdateUserDto dto) {

        if (dto == null) {
            throw new BadRequestException("Update request cannot be null");
        }
    }

    private String sanitizeQuery(String query) {
        return query == null ? "" : query.trim();
    }
}
