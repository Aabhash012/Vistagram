package com.vistagram.app.api.response;

import com.vistagram.app.domain.UserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter
@Builder
@Getter
public class UserDetailResponse {
    private Long id;
    private String userName;
    private LocalDateTime createdAt;
    private int postCount;
    private int followerCount;
    private int followingCount;

    public static UserDetailResponse fromUserDto(UserDto userDto){
        return UserDetailResponse.builder()
                .id(userDto.getId())
                .userName(userDto.getUsername())
                .createdAt(userDto.getCreatedAt())
                .postCount(userDto.getPostCount())
                .followerCount(userDto.getFollowerCount())
                .followingCount((userDto.getFollowingCount()))
                .build();
    }
}
