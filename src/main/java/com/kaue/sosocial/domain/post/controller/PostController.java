package com.kaue.sosocial.domain.post.controller;

import com.kaue.sosocial.commons.enums.StatusPost;
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
    public ResponseEntity<PostResponse> create(@RequestBody @Valid PostCreateRequest req,
                                               UriComponentsBuilder uriBuilder,
                                               @AuthenticationPrincipal UUID userId){
        var post = postService.createPost(req, userId);
        var uri  = uriBuilder.path("/post/{id}").buildAndExpand(post.id()).toUri();

        return ResponseEntity.created(uri).body(post);
    }

    @GetMapping("{userId}")
    public ResponseEntity<Page<PostResponse>> getAll(
            @PageableDefault(size = 15)Pageable pageable,
            @PathVariable UUID userId
            ){
        var posts = postService.getAllPostFromUser(pageable, userId);
        return ResponseEntity.ok(posts);
    }

    @PatchMapping("{postId}/active")
    public ResponseEntity<PostViewResponse> patchPostStatusActive(
            @PathVariable UUID postId,
            @AuthenticationPrincipal UUID userId){

        return ResponseEntity.ok(postService.alterStatusPost(StatusPost.ACTIVE.name(), postId, userId));

    }

    @PatchMapping("{postId}/private")
    public ResponseEntity<PostViewResponse> patchPostStatusPrivate(
            @PathVariable UUID postId,
            @AuthenticationPrincipal UUID userId){

        return ResponseEntity.ok(postService.alterStatusPost(StatusPost.PRIVATE.name(), postId, userId));

    }

    @PatchMapping("{postId}/delete")
    public ResponseEntity<PostViewResponse> patchPostStatusDelete(
            @PathVariable UUID postId,
            @AuthenticationPrincipal UUID userId){

        return ResponseEntity.ok(postService.alterStatusPost(StatusPost.DELETED.name(), postId, userId));

    }
}
