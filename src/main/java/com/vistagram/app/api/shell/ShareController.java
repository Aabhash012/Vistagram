package com.vistagram.app.api.shell;

import com.vistagram.app.domain.PostDto;
import com.vistagram.app.service.Interface.ShareService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import static com.vistagram.app.utils.Constants.ApiRoutes.Share.SHARED_BY_USER;
import static com.vistagram.app.utils.Constants.ApiRoutes.Share.SHARE_SHELL;
import static com.vistagram.app.utils.Constants.ApiRoutes.Share.SHARE_POST;
@RestController
@RequiredArgsConstructor
@RequestMapping(SHARE_SHELL)
public class ShareController {

    private final ShareService shareService;

    @PostMapping(SHARE_POST)
    public ResponseEntity<String> sharePost(
            @PathVariable Long postId,
            @RequestParam("userId") Long userId) {

        String shareLink = shareService.sharePost(postId, userId);
        return ResponseEntity.ok(shareLink);
    }
    @GetMapping(SHARED_BY_USER)
    public ResponseEntity<Page<PostDto>> getUserSharedPosts(
            @PathVariable Long userId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Page<PostDto> sharedPosts = shareService.getUserSharedPosts(userId, page, size);
        return ResponseEntity.ok(sharedPosts);
    }
}
