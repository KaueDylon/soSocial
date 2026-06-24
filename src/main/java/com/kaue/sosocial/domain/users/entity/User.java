package com.kaue.sosocial.domain.users.entity;

import com.kaue.sosocial.commons.enums.RoleUser;
import com.kaue.sosocial.commons.enums.StatusUser;
import com.kaue.sosocial.commons.enums.VisibilityProfile;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Email
    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_user",nullable = false)
    private RoleUser roleUser;

    @Enumerated(EnumType.STRING)
    @Column(name= "status_user",nullable = false)
    private StatusUser status;

    @Enumerated(EnumType.STRING)
    @Column(name="visibility_profile",nullable = false)
    private VisibilityProfile visibility;

    @Column(nullable = false)
    private String password;

    @Column(name= "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

}
