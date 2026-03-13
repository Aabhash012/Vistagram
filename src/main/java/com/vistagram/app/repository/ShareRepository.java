package com.vistagram.app.repository;

import com.vistagram.app.repository.entity.Post;
import com.vistagram.app.repository.entity.Share;
import com.vistagram.app.repository.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ShareRepository extends JpaRepository<Share, Long> {

    Optional<Share> findByUserIdAndPostId(Long userId, Long postId);

    @Query("""
        SELECT s.post FROM Share s
        WHERE s.user.id = :userId
        ORDER BY s.post.createdAt DESC
    """)
    Page<Post> findSharedPostsByUserId(@Param("userId") Long userId, Pageable pageable);

    @Modifying
    @Query("update Share s set s.shareCount = s.shareCount + 1 where s.user.id = :userId and s.post.id = :postId")
    int incrementShareCount(Long userId, Long postId);
}
