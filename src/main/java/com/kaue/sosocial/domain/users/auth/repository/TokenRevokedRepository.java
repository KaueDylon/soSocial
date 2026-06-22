package com.kaue.sosocial.domain.users.auth.repository;

import com.kaue.sosocial.domain.users.entity.TokenRevoked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TokenRevokedRepository extends JpaRepository<TokenRevoked, UUID> {

    boolean existsByTokenId(String tokenId);

}
