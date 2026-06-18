package com.kaue.sosocial.domain.like_post.entity;

import com.kaue.sosocial.domain.post.entity.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.kaue.sosocial.domain.users.entity.User;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "like_posts", uniqueConstraints = @UniqueConstraint(columnNames = {"id_post", "id_user"}))
@Getter
@Setter
@NoArgsConstructor
public class LikePost {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_post", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @Column(name= "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

}
