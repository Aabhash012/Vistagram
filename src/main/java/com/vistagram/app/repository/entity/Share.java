package com.vistagram.app.repository.entity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;
import lombok.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "shares")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Share {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
