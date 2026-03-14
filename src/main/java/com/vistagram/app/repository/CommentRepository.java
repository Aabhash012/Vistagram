package com.vistagram.app.repository;

import com.vistagram.app.repository.entity.Comments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comments, Long> {

    Page<Comments> findByPostIdOrderByCreatedAtDesc(Long postId, Pageable pageable);

}