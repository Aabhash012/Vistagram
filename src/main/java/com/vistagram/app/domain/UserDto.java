package com.vistagram.app.domain;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String username;
    private LocalDateTime createdAt;
    private int postCount;
    private int followerCount;
    private int followingCount;
}
