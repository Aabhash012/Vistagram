package com.vistagram.app.repository.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.util.List;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String imageUrl;
    private String caption;
    private String poiName;
    private String poiLocation;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder.Default
    @Column(nullable = false)
    private int likeCount = 0;

    @Builder.Default
    @Column(nullable = false)
    private int shareCount = 0;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Share> shares = new ArrayList<>();

    // Convenience methods
    public void addLike(Like like) {
        likes.add(like);
        like.setPost(this);
        likeCount++;
    }

    public void removeLike(Like like) {
        if (likes.remove(like)) {
            like.setPost(null);
            likeCount = Math.max(0, likeCount - 1);
        }
    }

    public void addShare(Share share) {
        shares.add(share);
        share.setPost(this);
        shareCount++;
    }
}