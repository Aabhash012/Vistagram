package com.vistagram.app.api.response;

import com.vistagram.app.domain.CommentDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Builder
@Getter
public class CommentResponse {
    private Long id;
    private String comment;
    private Long userId;
    private LocalDateTime createdAt;

    public static CommentResponse fromDto(CommentDto commentDto){
        return CommentResponse.builder()
                .id(commentDto.getId())
                .userId(commentDto.getUserId())
                .comment(commentDto.getComment())
                .createdAt(commentDto.getCreatedAt())
                .build();
    }
}
