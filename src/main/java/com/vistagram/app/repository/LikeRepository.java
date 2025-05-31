package com.vistagram.app.repository;

import com.vistagram.app.repository.entity.Like;
import com.vistagram.app.repository.entity.Post;
import com.vistagram.app.repository.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByUserAndPost(User user, Post post);
    Boolean existsByUserAndPost(User user, Post post);
    Long countByPost(Post post);

    @Query("SELECT l.post.id FROM Like l WHERE l.user.id = :userId")
    Page<Long> findLikedPostIdsByUserId(@Param("userId") Long userId, Pageable pageable);
}
