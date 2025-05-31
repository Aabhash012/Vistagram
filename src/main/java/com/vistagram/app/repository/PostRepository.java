package com.vistagram.app.repository;

import com.vistagram.app.repository.entity.Post;
import com.vistagram.app.repository.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findByUser(User user, Pageable pageable);
    @Query("SELECT p FROM Post p WHERE " +
            "LOWER(p.caption) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(p.poiName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(p.poiLocation) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<Post> searchPosts(@Param("query") String query, Pageable pageable);

    @Query("SELECT p FROM Post p ORDER BY p.createdAt DESC")
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.user.id IN :userIds ORDER BY p.createdAt DESC")
    Page<Post> findByUserIds(@Param("userIds") List<Long> userIds, Pageable pageable);
}
