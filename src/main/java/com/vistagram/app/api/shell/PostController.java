package com.vistagram.app.api.shell;

import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_SHELL_POST)
public class PostController {

    private final PostService postService;

    @PostMapping(CREATE_POST)
    public ResponseEntity<PostDto> createPost(
            @RequestParam("image") MultipartFile image,
            @RequestParam("caption") String caption,
            @RequestParam(value = "poiName", required = false) String poiName,
            @RequestParam(value = "poiLocation", required = false) String poiLocation,
            @AuthenticationPrincipal UserPrincipal currentUser) {

        PostDto postDto = postService.createPost(
                image, caption, poiName, poiLocation, currentUser.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(postDto);
    }

    @GetMapping(GET_TIMELINE)
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
}
