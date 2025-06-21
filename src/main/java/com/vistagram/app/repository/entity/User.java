package com.vistagram.app.repository.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder.Default
    @Column(nullable = false)
    private int postCount = 0;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Post> posts = new ArrayList<>();

    public void addPost(Post post) {
        if (post != null) {
            posts.add(post);
            post.setUser(this);
            postCount++; // keep the count in sync
        }
    }
    public void removePost(Post post) {
        if (posts.remove(post)) {
            post.setUser(null);
            postCount = Math.max(0, postCount - 1);
        }
    }
}
