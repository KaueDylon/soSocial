package com.kaue.sosocial.domain.post.repository;

import com.kaue.sosocial.commons.enums.StatusPost;
import com.kaue.sosocial.domain.post.dto.PostResponse;
import com.kaue.sosocial.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {

    @Query("UPDATE Post p SET p.status = 'PRIVATE' ")
    void alterPostStatusPrivate();

    @Query("UPDATE Post p SET p.status = 'PUBLIC' ")
    void alterPostStatusPublic();

    @Query("UPDATE Post p SET p.status = 'DELETED' ")
    void alterPostStatusDeleted();

    @Query("SELECT p FROM Post p WHERE p.status = :status AND p.user.id = :userId")
    Page<PostResponse> getAllPostsStatusPublic(@Param("userId") UUID userId,
                                               @Param("status") StatusPost status,
                                               Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.user.id = :userId AND p.id = :postId")
    Optional<Post> getPostByIdAndUserId(@Param("userId") UUID userId,
                                        @Param("postId") UUID postId);
}
