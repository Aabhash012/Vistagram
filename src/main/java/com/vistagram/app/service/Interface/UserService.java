package com.vistagram.app.service.Interface;

import com.vistagram.app.domain.UserDto;
import com.vistagram.app.domain.UpdateUserDto;
import org.springframework.data.domain.Page;

public interface UserService {

    UserDto getUserProfile(Long userId);
    UserDto updateUserProfile(UpdateUserDto updateUserDto);
    Page<UserDto> searchUsers(String query, int page, int size);
}
