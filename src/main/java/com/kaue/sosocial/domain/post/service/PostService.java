package com.kaue.sosocial.domain.post.service;

import com.kaue.sosocial.commons.enums.StatusPost;
import com.kaue.sosocial.commons.enums.VisibilityPost;
import com.kaue.sosocial.domain.post.dto.PostCreateRequest;
import com.kaue.sosocial.domain.post.dto.PostResponse;
import com.kaue.sosocial.domain.post.dto.PostViewResponse;
import com.kaue.sosocial.domain.post.entity.Post;
import com.kaue.sosocial.domain.post.repository.PostRepository;
import com.kaue.sosocial.domain.users.auth.repository.UserAuthRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserAuthRepository userAuthRepository;

    public PostService(
            PostRepository postRepository,
            UserAuthRepository userAuthRepository) {

        this.postRepository = postRepository;
        this.userAuthRepository = userAuthRepository;
    }

    public Page<PostResponse> getAllPostFromUser(
            Pageable pageable,
            UUID userId) {

        return postRepository.getAllPostsStatusPublic(
                userId,
                StatusPost.ACTIVE,
                pageable
        );
    }

    @Transactional
    public PostResponse createPost(
            PostCreateRequest req,
            UUID userId) {

        if (req.text() == null || req.text().isBlank()) {
            throw new IllegalArgumentException(
                    "Texto da postagem não deve ser vazio"
            );
        }

        var user = userAuthRepository.findById(userId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Usuário não existe"));

        var post = new Post();

        post.setUser(user);
        post.setText(req.text());
        post.setStatus(StatusPost.ACTIVE);
        post.setVisibility(VisibilityPost.PUBLIC);
        post.setCreatedAt(LocalDateTime.now());

        postRepository.save(post);

        return new PostResponse(
                post.getId(),
                post.getText(),
                post.getCreatedAt()
        );
    }

    @Transactional
    public PostViewResponse alterStatusPost(
            StatusPost status,
            UUID postId,
            UUID userId) {

        var post = postRepository
                .getPostByIdAndUserId(userId, postId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Post não existe"));

        post.setStatus(status);

        return new PostViewResponse(post);
    }

    @Transactional
    public PostViewResponse alterVisibilityPost(
            VisibilityPost visibility,
            UUID postId,
            UUID userId) {

        var post = postRepository
                .getPostByIdAndUserId(userId, postId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Post não existe"));

        post.setVisibility(visibility);

        return new PostViewResponse(post);
    }

    @Transactional(readOnly = true)
    public PostViewResponse getViewPostByUser(UUID postId) {

        var post = postRepository.findById(postId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Post não existe"));

        return new PostViewResponse(post);
    }
}