package com.vistagram.app.api.request;

import com.vistagram.app.domain.CommentDto;
import com.vistagram.app.domain.UpdateUserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddCommentRequest {
    private String comment;
    private Long postId;

    public static CommentDto toDto(AddCommentRequest addCommentRequest){
        return CommentDto.builder()
                .comment(addCommentRequest.getComment())
                .postId(addCommentRequest.getPostId())
                .build();
    }
}
