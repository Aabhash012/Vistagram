package com.vistagram.app.mapper;

import com.vistagram.app.domain.UserDto;
import com.vistagram.app.repository.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto mapToDto(User user) {

        if (user == null) {
            return null;
        }
        return UserDto.builder()
                .id(user.getId())
                .userName(user.getUsername())
                .createdAt(user.getCreatedAt())
                .postCount(user.getPostCount())
                .build();
    }
}
