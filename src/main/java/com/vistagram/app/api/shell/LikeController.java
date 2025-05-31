package com.vistagram.app.api.shell;

import com.vistagram.app.domain.PostDto;
import com.vistagram.app.service.Interface.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.vistagram.app.utils.Constants.ApiRoutes.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_SHELL_LIKE)
public class LikeController {
    private final LikeService likeService;

    @PostMapping(LIKE_POST)
    public ResponseEntity<Void> likePost(
            @PathVariable Long postId,
            @RequestParam("userId") Long userId) {

        likeService.likePost(postId, userId);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping(UNLIKE_POST)
    public ResponseEntity<Void> unlikePost(
            @PathVariable Long postId,
            @RequestParam("userId") Long userId) {

        likeService.likePost(postId, userId);
        return ResponseEntity.ok().build();
    }
    @GetMapping(IS_LIKED_BY)
    public ResponseEntity<Boolean> isPostLikedByUser(
            @PathVariable Long postId,
            @RequestParam("userId") Long userId) {

        boolean isLiked = likeService.isPostLikedByUser(postId, userId);
        return ResponseEntity.ok(isLiked);
    }

    @GetMapping(GET_USER_LIKED_POSTS)
    public ResponseEntity<Page<PostDto>> getUserLikedPosts(
            @PathVariable Long userId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Page<PostDto> likedPosts = likeService.getUserLikedPosts(userId, page, size);
        return ResponseEntity.ok(likedPosts);
    }
}
