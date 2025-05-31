package com.vistagram.app.repository;
import com.vistagram.app.repository.entity.Like;
import com.vistagram.app.repository.entity.Post;
import com.vistagram.app.repository.entity.Share;
import com.vistagram.app.repository.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface ShareRepository extends JpaRepository<Share, Long> {
    Long countByPost(Post post);

    @Query("SELECT s.post.id FROM Share s WHERE s.user.id = :userId")
    Page<Long> findSharedPostIdsByUserId(@Param("userId") Long userId, Pageable pageable);
}
