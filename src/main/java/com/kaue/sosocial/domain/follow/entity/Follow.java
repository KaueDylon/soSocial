package com.kaue.sosocial.domain.follow.entity;

import com.kaue.sosocial.domain.users.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "follows", uniqueConstraints = @UniqueConstraint(columnNames = {"id_follower", "id_following"}))
@Getter
@Setter
@NoArgsConstructor
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_follower", nullable = false)
    private User userFollower;

    @ManyToOne
    @JoinColumn(name = "id_following", nullable = false)
    private User userFollowing;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

}
