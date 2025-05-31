package com.vistagram.app.api.shell;

import com.vistagram.app.domain.PostDto;
import com.vistagram.app.service.Interface.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import static com.vistagram.app.utils.Constants.ApiRoutes.Post.CREATE_POST;
import static com.vistagram.app.utils.Constants.ApiRoutes.Post.DELETE_POST;
import static com.vistagram.app.utils.Constants.ApiRoutes.Post.GET_POST_BY_ID;
import static com.vistagram.app.utils.Constants.ApiRoutes.Post.POST_SHELL;
import static com.vistagram.app.utils.Constants.ApiRoutes.Post.GET_POST_BY_USER_ID;
import static com.vistagram.app.utils.Constants.ApiRoutes.Post.GET_TIMELINE;
import static com.vistagram.app.utils.Constants.ApiRoutes.Post.SEARCH_POST;

@RestController
@RequiredArgsConstructor
@RequestMapping(POST_SHELL)
public class PostController {

    private final PostService postService;

    @PostMapping(CREATE_POST)
    public ResponseEntity<PostDto> createPost(
            @RequestParam("image") MultipartFile image,
            @RequestParam("caption") String caption,
            @RequestParam(value = "poiName", required = false) String poiName,
            @RequestParam(value = "poiLocation", required = false) String poiLocation,
            @RequestParam("userId") Long userId) {

        PostDto postDto = postService.createPost(image, caption, poiName, poiLocation, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(postDto);
    }

    @GetMapping(GET_TIMELINE)
    public ResponseEntity<Page<PostDto>> getTimeline(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam("currentUserId") Long currentUserId) {

        Page<PostDto> posts = postService.getTimeline(page, size, currentUserId);
        return ResponseEntity.ok(posts);
    }
    @GetMapping(GET_POST_BY_ID)
    public ResponseEntity<PostDto> getPost(@PathVariable Long postId) {
        PostDto postDto = postService.getPostById(postId);
        return ResponseEntity.ok(postDto);
    }
    @GetMapping(GET_POST_BY_USER_ID)
    public ResponseEntity<Page<PostDto>> getUserPosts(
            @PathVariable Long userId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Page<PostDto> posts = postService.getUserPosts(userId, page, size);
        return ResponseEntity.ok(posts);
    }

    @GetMapping(SEARCH_POST)
    public ResponseEntity<Page<PostDto>> searchPosts(
            @RequestParam("query") String query,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Page<PostDto> results = postService.searchPosts(query, page, size);
        return ResponseEntity.ok(results);
    }

    @DeleteMapping(DELETE_POST)
    public ResponseEntity<Void> deletePost(
            @PathVariable Long postId,
            @RequestParam("userId") Long userId) {

        postService.deletePost(postId, userId);
        return ResponseEntity.noContent().build();
    }
}
