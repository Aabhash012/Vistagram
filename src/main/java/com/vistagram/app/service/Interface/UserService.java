package com.vistagram.app.service.Interface;

import com.vistagram.app.domain.UserDto;
import com.vistagram.app.domain.UserUpdateDto;
import org.springframework.data.domain.Page;

public interface UserService {

    UserDto getUserProfile(Long userId);
    UserDto updateUserProfile(Long userId, UserUpdateDto userUpdateDto);
    Page<UserDto> searchUsers(String query, int page, int size);
}
