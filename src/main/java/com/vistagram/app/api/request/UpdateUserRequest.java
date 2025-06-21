package com.vistagram.app.api.request;

import com.vistagram.app.domain.UpdateUserDto;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserRequest {

    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    private String username;

    public static UpdateUserDto toDto(UpdateUserRequest updateUserRequest){
        return UpdateUserDto.builder()
                .username(updateUserRequest.getUsername())
                .build();
    }
}
