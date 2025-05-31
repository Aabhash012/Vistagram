package com.vistagram.app.domain;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateDto {

    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    private String username;
}
