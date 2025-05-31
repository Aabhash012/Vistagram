package com.vistagram.app.api.shell;

import com.vistagram.app.domain.UserDto;
import com.vistagram.app.domain.UserUpdateDto;
import com.vistagram.app.service.Interface.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import static com.vistagram.app.utils.Constants.ApiRoutes.User.USER_SHELL;
import static com.vistagram.app.utils.Constants.ApiRoutes.User.SEARCH_USER;
import static com.vistagram.app.utils.Constants.ApiRoutes.User.GET_USER_PROFILE;
import static com.vistagram.app.utils.Constants.ApiRoutes.User.UPDATE_USER_PROFILE;

@RestController
@RequiredArgsConstructor
@RequestMapping(USER_SHELL)
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
            @Valid @RequestBody UserUpdateDto userUpdateDto) {

        UserDto updatedUser = userService.updateUserProfile(userId, userUpdateDto);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping(SEARCH_USER)
    public ResponseEntity<Page<UserDto>> searchUsers(
            @RequestParam("query") String query,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Page<UserDto> results = userService.searchUsers(query, page, size);
        return ResponseEntity.ok(results);
    }
}
