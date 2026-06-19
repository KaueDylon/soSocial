package com.kaue.sosocial.domain.users.repository;

import com.kaue.sosocial.domain.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByName(String name);
    Optional<User> findByEmail(String name);
    boolean existsByEmail (String email);

}
