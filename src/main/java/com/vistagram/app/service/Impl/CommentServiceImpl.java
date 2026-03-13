package com.vistagram.app.service.Impl;

import com.vistagram.app.domain.CommentDto;
import com.vistagram.app.repository.CommentRepository;
import com.vistagram.app.repository.LikeRepository;
import com.vistagram.app.repository.entity.Comments;
import com.vistagram.app.repository.entity.Post;
import com.vistagram.app.repository.entity.User;
import com.vistagram.app.service.Interface.CommentService;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BaseService baseService;
    public CommentDto addComment(CommentDto commentDto, Long userId){
        Post post = baseService.getPostOrThrow(commentDto.getPostId());
        User user = baseService.getUserOrThrow(userId);

        Comments comment = new Comments();
        comment.setComment(commentDto.getComment());
        comment.setPost(post);
        comment.setUser(user);
        commentRepository.save(comment);
        return commentDto;

    }
}
