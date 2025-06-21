package com.vistagram.app.mapper;

import com.vistagram.app.domain.UserDto;
import com.vistagram.app.repository.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    public UserDto mapToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .userName(user.getUsername())
                .createdAt(user.getCreatedAt())
                .postCount(user.getPostCount())
            .build();

    }
}
