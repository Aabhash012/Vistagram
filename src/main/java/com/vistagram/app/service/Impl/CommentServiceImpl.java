package com.vistagram.app.service.Impl;

import com.vistagram.app.domain.CommentDto;
import com.vistagram.app.repository.CommentRepository;
import com.vistagram.app.repository.PostRepository;
import com.vistagram.app.repository.entity.Comments;
import com.vistagram.app.repository.entity.Post;
import com.vistagram.app.repository.entity.User;
import com.vistagram.app.service.Interface.AiModerationService;
import com.vistagram.app.service.Interface.CommentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final AiModerationService aiModerationService;

    @Override
    @Transactional
    public CommentDto addComment(CommentDto commentDto, Long userId) {

        boolean isToxic = aiModerationService.isToxic(commentDto.getComment());
        if (isToxic) {
            throw new RuntimeException("Comment rejected due to toxic content.");
        }
        Comments comment = Comments.builder()
                .comment(commentDto.getComment())
                .post(Post.builder().id(commentDto.getPostId()).build())
                .user(User.builder().id(userId).build())
                .build();
        Comments savedComment = commentRepository.save(comment);
        postRepository.incrementCommentCount(commentDto.getPostId());
        return CommentDto.builder()
                .id(savedComment.getId())
                .comment(savedComment.getComment())
                .postId(commentDto.getPostId())
                .userId(userId)
                .createdAt(savedComment.getCreatedAt())
                .build();
    }
    @Override
    public Page<CommentDto> getComments(Long postId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        return commentRepository
                .findByPostIdOrderByCreatedAtDesc(postId, pageable)
                .map(comment -> CommentDto.builder()
                        .id(comment.getId())
                        .comment(comment.getComment())
                        .postId(postId)
                        .userId(comment.getUser().getId())
                        .createdAt(comment.getCreatedAt())
                        .build());
    }
}
