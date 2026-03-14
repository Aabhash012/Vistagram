package com.vistagram.app.repository;

import com.vistagram.app.repository.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    @Query("""
        SELECT u FROM User u
        WHERE LOWER(u.username) LIKE CONCAT('%', LOWER(:query), '%')
        ORDER BY u.username
    """)
    Page<User> searchByUsername(@Param("query") String query, Pageable pageable);
}
