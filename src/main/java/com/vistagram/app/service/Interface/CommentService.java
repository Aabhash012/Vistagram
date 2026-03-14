package com.vistagram.app.service.Interface;

import com.vistagram.app.domain.CommentDto;
import org.springframework.data.domain.Page;

public interface CommentService {
    CommentDto addComment(CommentDto commentDto, Long userName);
    Page<CommentDto> getComments(Long postId, int page, int size);
}
