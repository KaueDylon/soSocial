package com.kaue.sosocial.domain.post.repository;

import com.kaue.sosocial.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {



}
