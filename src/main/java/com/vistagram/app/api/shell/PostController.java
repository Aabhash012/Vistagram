package com.vistagram.app.api.shell;

import com.vistagram.app.domain.PostDto;
import com.vistagram.app.service.Interface.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import static com.vistagram.app.utils.Constants.ApiRoutes.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_SHELL_POST)
public class PostController {

    private final PostService postService;

    //@PostMapping(CREATE_POST)
    public ResponseEntity<PostDto> createPost(
            @RequestParam("image") MultipartFile image,
            @RequestParam("caption") String caption,
            @RequestParam(value = "poiName", required = false) String poiName,
            @RequestParam(value = "poiLocation", required = false) String poiLocation,
            @RequestParam("userId") Long userId) {

        PostDto postDto = postService.createPost(image, caption, poiName, poiLocation, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(postDto);
    }

    //@GetMapping(GET_TIMELINE)
    public ResponseEntity<Page<PostDto>> getTimeline(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Page<PostDto> posts = postService.getTimeline(page, size);
        return ResponseEntity.ok(posts);
    }
    @GetMapping(GET_POST)
    public ResponseEntity<PostDto> getPost(@PathVariable Long postId) {
        PostDto postDto = postService.getPostById(postId);
        return ResponseEntity.ok(postDto);
    }
    @GetMapping("/user/{userId}")
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
