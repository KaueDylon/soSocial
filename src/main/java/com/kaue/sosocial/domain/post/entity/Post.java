package com.kaue.sosocial.domain.post.entity;

import com.kaue.sosocial.commons.enums.StatusPost;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.kaue.sosocial.domain.users.entity.User;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name= "status", nullable = false)
    private StatusPost status;

    @Column(length = 380)
    private String text;

    @Column(name= "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

}
