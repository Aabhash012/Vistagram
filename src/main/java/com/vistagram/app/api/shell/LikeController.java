package com.vistagram.app.api.shell;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_SHELL_LIKE)
public class LikeController {
    private final LikeService likeService;

    @PostMapping(LIKE_POST)
    public ResponseEntity<Void> likePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal UserPrincipal currentUser) {

        likeService.likePost(postId, currentUser.getId());
        return ResponseEntity.ok().build();
    }
    @DeleteMapping(UNLIKE_POST)
    public ResponseEntity<Void> unlikePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal UserPrincipal currentUser) {

        likeService.unlikePost(postId, currentUser.getId());
        return ResponseEntity.noContent().build();
    }
}
