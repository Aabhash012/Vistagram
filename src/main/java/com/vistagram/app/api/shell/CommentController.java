package com.vistagram.app.api.shell;

import com.vistagram.app.api.request.AddCommentRequest;
import com.vistagram.app.api.response.CommentResponse;
import com.vistagram.app.api.response.UserDetailResponse;
import com.vistagram.app.domain.CommentDto;
import com.vistagram.app.service.Interface.CommentService;
import com.vistagram.app.service.Interface.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.vistagram.app.utils.Constants.ApiRoutes.Comment.COMMENT_SHELL;


@RestController
@RequiredArgsConstructor
@RequestMapping(COMMENT_SHELL)
public class CommentController {
    private final CommentService commentService;
    @PostMapping
    public ResponseEntity<CommentResponse> addComment(@RequestParam("userId") Long userId,
                                                      @RequestBody AddCommentRequest addCommentRequest){

        CommentDto commentDto = AddCommentRequest.toDto(addCommentRequest);
        commentDto = commentService.addComment(commentDto,userId);
        CommentResponse commentResponse = CommentResponse.fromDto(commentDto);
        return ResponseEntity.ok(commentResponse);

    }
}
