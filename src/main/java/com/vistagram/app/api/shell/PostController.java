package com.vistagram.app.api.shell;

import com.vistagram.app.api.response.PostDetailResponse;
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
    public ResponseEntity<PostDetailResponse> createPost(
            @RequestParam("image") MultipartFile image,
            @RequestParam("caption") String caption,
            @RequestParam(value = "poiName", required = false) String poiName,
            @RequestParam(value = "poiLocation", required = false) String poiLocation,
            @RequestParam("userId") Long userId) {

        PostDto postDto = postService.createPost(image, caption, poiName, poiLocation, userId);
        PostDetailResponse createPostResponse = PostDetailResponse.fromPostDto(postDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createPostResponse);
    }

    @GetMapping(GET_TIMELINE)
    public ResponseEntity<Page<PostDetailResponse>> getTimeline(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam("currentUserId") Long currentUserId) {

        Page<PostDto> posts = postService.getTimeline(page, size, currentUserId);
        Page<PostDetailResponse> getTimelineResponse = posts.map(PostDetailResponse::fromPostDto);
        return ResponseEntity.ok(getTimelineResponse);
    }
    @GetMapping(GET_POST_BY_ID)
    public ResponseEntity<PostDetailResponse> getPost(@PathVariable Long postId) {
        PostDto postDto = postService.getPostById(postId);
        PostDetailResponse getPostById = PostDetailResponse.fromPostDto(postDto);
        return ResponseEntity.ok(getPostById);
    }
    @GetMapping(GET_POST_BY_USER_ID)
    public ResponseEntity<Page<PostDetailResponse>> getUserPosts(
            @PathVariable Long userId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Page<PostDto> posts = postService.getUserPosts(userId, page, size);
        Page<PostDetailResponse> getUserPosts = posts.map(PostDetailResponse::fromPostDto);
        return ResponseEntity.ok(getUserPosts);
    }

    @GetMapping(SEARCH_POST)
    public ResponseEntity<Page<PostDetailResponse>> searchPosts(
            @RequestParam("query") String query,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Page<PostDto> results = postService.searchPosts(query, page, size);
        Page<PostDetailResponse> searchPostResponse = results.map(PostDetailResponse::fromPostDto);
        return ResponseEntity.ok(searchPostResponse);
    }

    @DeleteMapping(DELETE_POST)
    public ResponseEntity<Void> deletePost(
            @PathVariable Long postId,
            @RequestParam("userId") Long userId) {

        postService.deletePost(postId, userId);
        return ResponseEntity.noContent().build();
    }
}
