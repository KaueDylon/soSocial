package com.kaue.sosocial.domain.like_comment.entity;

import com.kaue.sosocial.domain.comments.entity.Comment;
import com.kaue.sosocial.domain.users.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "like_comments", uniqueConstraints = @UniqueConstraint(columnNames = {"id_comment", "id_user"}))
@Getter
@Setter
@NoArgsConstructor
public class LikeComment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_comment", nullable = false)
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

}
