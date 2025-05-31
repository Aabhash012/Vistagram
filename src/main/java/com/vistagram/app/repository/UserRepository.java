package com.vistagram.app.repository;

import com.vistagram.app.repository.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.username LIKE %:query%")
    Page<User> searchByUsername(@Param("query") String query, Pageable pageable);
}
