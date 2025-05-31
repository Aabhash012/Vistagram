package com.vistagram.app.api.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static com.vistagram.app.utils.Constants.ApiRoutes.API_SHELL_SHARE;
import static com.vistagram.app.utils.Constants.ApiRoutes.SHARE_POST;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_SHELL_SHARE)
public class ShareController {

    private final ShareController shareController;

    @PostMapping(SHARE_POST)
    public ResponseEntity<String> sharePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal UserPrincipal currentUser) {

        String shareLink = shareService.sharePost(postId, currentUser.getId());
        return ResponseEntity.ok(shareLink);
    }
}
