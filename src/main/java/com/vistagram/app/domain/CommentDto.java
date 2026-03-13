package com.vistagram.app.domain;

import com.vistagram.app.api.response.CommentResponse;
import com.vistagram.app.api.response.PostDetailResponse;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {
    private String comment;
    private Long postId;
    private Long id;
    private Long userId;
    private LocalDateTime createdAt;


}
