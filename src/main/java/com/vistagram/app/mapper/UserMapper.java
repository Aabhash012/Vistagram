package com.vistagram.app.mapper;

import com.vistagram.app.domain.UserDto;
import com.vistagram.app.repository.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;
    public UserDto mapToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    public User mapToEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }
}
