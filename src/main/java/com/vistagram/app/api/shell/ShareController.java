package com.vistagram.app.api.shell;

import com.vistagram.app.domain.PostDto;
import com.vistagram.app.service.Interface.ShareService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.vistagram.app.utils.Constants.ApiRoutes.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_SHELL_SHARE)
public class ShareController {

    private final ShareService shareService;

    @PostMapping(SHARE_POST)
    public ResponseEntity<String> sharePost(
            @PathVariable Long postId,
            @RequestParam("userId") Long userId) {

        String shareLink = shareService.sharePost(postId, userId);
        return ResponseEntity.ok(shareLink);
    }
    @GetMapping(GET_USER_SHARED_POST)
    public ResponseEntity<Page<PostDto>> getUserSharedPosts(
            @PathVariable Long userId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Page<PostDto> sharedPosts = shareService.getUserSharedPosts(userId, page, size);
        return ResponseEntity.ok(sharedPosts);
    }
}
