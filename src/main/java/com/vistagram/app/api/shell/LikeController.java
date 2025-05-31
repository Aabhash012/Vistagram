package com.vistagram.app.api.shell;

import com.vistagram.app.domain.PostDto;
import com.vistagram.app.service.Interface.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import static com.vistagram.app.utils.Constants.ApiRoutes.Like.LIKED_BY_USER;
import static com.vistagram.app.utils.Constants.ApiRoutes.Like.LIKE_SHELL;
import static com.vistagram.app.utils.Constants.ApiRoutes.Like.LIKE_POST;
import static com.vistagram.app.utils.Constants.ApiRoutes.Like.POST_STATUS;
import static com.vistagram.app.utils.Constants.ApiRoutes.Like.UNLIKE_POST;

@RestController
@RequiredArgsConstructor
@RequestMapping(LIKE_SHELL)
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
    @GetMapping(POST_STATUS)
    public ResponseEntity<Boolean> isPostLikedByUser(
            @PathVariable Long postId,
            @RequestParam("userId") Long userId) {

        boolean isLiked = likeService.isPostLikedByUser(postId, userId);
        return ResponseEntity.ok(isLiked);
    }

    @GetMapping(LIKED_BY_USER)
    public ResponseEntity<Page<PostDto>> getUserLikedPosts(
            @PathVariable Long userId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Page<PostDto> likedPosts = likeService.getUserLikedPosts(userId, page, size);
        return ResponseEntity.ok(likedPosts);
    }
}
