package com.kaue.sosocial.domain.like_post.repository;

import com.kaue.sosocial.domain.like_post.entity.LikePost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LikePostRepository extends JpaRepository<LikePost, UUID> {



}
