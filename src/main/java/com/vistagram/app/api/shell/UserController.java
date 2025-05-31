package com.vistagram.app.api.shell;

import com.vistagram.app.domain.UserDto;
import com.vistagram.app.domain.UserUpdateDto;
import com.vistagram.app.service.Interface.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.vistagram.app.utils.Constants.ApiRoutes.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_SHELL_USER)
public class UserController {

    private final UserService userService;


    @GetMapping(GET_USER_PROFILE)
    public ResponseEntity<UserDto> getUserProfile(@PathVariable Long userId) {
        UserDto userDto = userService.getUserProfile(userId);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping(UPDATE_USER_PROFILE)
    public ResponseEntity<UserDto> updateUserProfile(
            @PathVariable Long userId,
            @RequestBody UserUpdateDto userUpdateDto) {

        UserDto updatedUser = userService.updateUserProfile(userId, userUpdateDto);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping(SEARCH)
    public ResponseEntity<Page<UserDto>> searchUsers(
            @RequestParam("query") String query,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Page<UserDto> results = userService.searchUsers(query, page, size);
        return ResponseEntity.ok(results);
    }
}
