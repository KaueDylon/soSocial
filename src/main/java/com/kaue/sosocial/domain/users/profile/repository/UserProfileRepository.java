package com.kaue.sosocial.domain.users.profile.repository;

import com.kaue.sosocial.domain.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserProfileRepository extends JpaRepository<User, UUID> {

}
