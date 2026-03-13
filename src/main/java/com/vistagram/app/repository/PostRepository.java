package com.vistagram.app.repository;

import com.vistagram.app.repository.entity.Post;
import com.vistagram.app.repository.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    @Query("""
            SELECT p FROM Post p
            WHERE LOWER(p.caption) LIKE LOWER(CONCAT('%', :query, '%'))
            OR LOWER(p.poiName) LIKE LOWER(CONCAT('%', :query, '%'))
            OR LOWER(p.poiLocation) LIKE LOWER(CONCAT('%', :query, '%'))
            ORDER BY p.createdAt DESC
            """)
    Page<Post> searchPosts(@Param("query") String query, Pageable pageable);

    @Query("""
        SELECT p FROM Post p
        JOIN Like l ON l.post.id = p.id
        WHERE l.user.id = :userId
        ORDER BY p.createdAt DESC
    """)
    Page<Post> findLikedPostsByUserId(@Param("userId") Long userId, Pageable pageable);

    @Modifying
    @Query("update Post p set p.likeCount = p.likeCount + 1 where p.id = :postId")
    void incrementLikeCount(@Param("postId") Long postId);

    @Modifying
    @Query("update Post p set p.likeCount = p.likeCount - 1 where p.id = :postId and p.likeCount > 0")
    void decrementLikeCount(@Param("postId") Long postId);

    @Modifying
    @Query("update Post p set p.shareCount = p.shareCount + 1 where p.id = :postId")
    void incrementShareCount(Long postId);
}