package com.vistagram.app.service.Interface;

import com.vistagram.app.domain.CommentDto;
import com.vistagram.app.domain.PostDto;
import org.springframework.web.multipart.MultipartFile;

public interface CommentService {
    CommentDto addComment(CommentDto commentDto, Long userName);
}
