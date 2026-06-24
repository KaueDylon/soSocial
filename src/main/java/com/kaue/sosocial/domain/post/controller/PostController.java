package com.kaue.sosocial.domain.post.controller;

import com.kaue.sosocial.commons.enums.StatusPost;
import com.kaue.sosocial.commons.enums.VisibilityPost;
import com.kaue.sosocial.domain.post.dto.PostCreateRequest;
import com.kaue.sosocial.domain.post.dto.PostResponse;
import com.kaue.sosocial.domain.post.dto.PostViewResponse;
import com.kaue.sosocial.domain.post.service.PostService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostResponse> create(
            @RequestBody @Valid PostCreateRequest req,
            UriComponentsBuilder uriBuilder,
            @AuthenticationPrincipal UUID userId) {

        var post = postService.createPost(req, userId);

        var uri = uriBuilder
                .path("/post/{id}")
                .buildAndExpand(post.id())
                .toUri();

        return ResponseEntity.created(uri).body(post);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<PostResponse>> getAll(
            @PageableDefault(size = 15) Pageable pageable,
            @PathVariable UUID userId) {

        return ResponseEntity.ok(
                postService.getAllPostFromUser(pageable, userId)
        );
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostViewResponse> getByPostId(
            @PathVariable UUID postId) {

        return ResponseEntity.ok(
                postService.getViewPostByUser(postId)
        );
    }

    @PatchMapping("/{postId}/status")
    public ResponseEntity<PostViewResponse> updateStatus(
            @PathVariable UUID postId,
            @RequestParam StatusPost status,
            @AuthenticationPrincipal UUID userId) {

        return ResponseEntity.ok(
                postService.alterStatusPost(status, postId, userId)
        );
    }

    @PatchMapping("/{postId}/visibility")
    public ResponseEntity<PostViewResponse> updateVisibility(
            @PathVariable UUID postId,
            @RequestParam VisibilityPost visibility,
            @AuthenticationPrincipal UUID userId) {

        return ResponseEntity.ok(
                postService.alterVisibilityPost(visibility, postId, userId)
        );
    }
}