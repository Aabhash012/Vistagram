package com.vistagram.app.api.shell;

import com.vistagram.app.api.request.AddCommentRequest;
import com.vistagram.app.api.response.CommentResponse;
import com.vistagram.app.domain.CommentDto;
import com.vistagram.app.service.Interface.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.vistagram.app.utils.Constants.ApiRoutes.Comment.*;
import static com.vistagram.app.utils.Constants.ApiRoutes.Post.POST_SHELL;


@RestController
@RequiredArgsConstructor
@RequestMapping(POST_SHELL)
public class CommentController {

    private final CommentService commentService;

    @PostMapping(ADD_COMMENT)
    public ResponseEntity<CommentResponse> addComment(
            @RequestParam("userId") Long userId,
            @RequestBody @Valid AddCommentRequest request) {

        CommentDto dto = AddCommentRequest.toDto(request);
        CommentDto savedComment = commentService.addComment(dto, userId);
        return ResponseEntity.ok(CommentResponse.fromDto(savedComment));
    }
    @GetMapping(GET_POST_COMMENTS)
    public ResponseEntity<Page<CommentResponse>> getComments(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<CommentDto> comments = commentService.getComments(postId, page, size);
        return ResponseEntity.ok(comments.map(CommentResponse::fromDto));
    }
}
