package com.vistagram.app.repository;

import com.vistagram.app.repository.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Set;

public interface LikeRepository extends JpaRepository<Like, Long> {

    boolean existsByUserIdAndPostId(Long userId, Long postId);
    void deleteByUserIdAndPostId(Long userId, Long postId);

    @Query("""
    SELECT l.post.id
    FROM Like l
    WHERE l.user.id = :userId
""")
    Set<Long> findPostIdsLikedByUser(@Param("userId") Long userId);
}