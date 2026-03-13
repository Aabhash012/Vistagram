package com.vistagram.app.repository;

import com.vistagram.app.repository.entity.Comments;
import com.vistagram.app.repository.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comments, Long> {
}
