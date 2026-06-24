package com.kaue.sosocial.domain.like_post.controller;

import com.kaue.sosocial.domain.like_post.dto.LikePostRequest;
import com.kaue.sosocial.domain.like_post.dto.LikePostResponse;
import com.kaue.sosocial.domain.like_post.service.LikePostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("/LikePost")
@RequiredArgsConstructor
public class LikePostController {

    private final LikePostService likePostService;

    @PostMapping
    public ResponseEntity<LikePostResponse> saveLike(
            @RequestBody @Valid LikePostRequest req,
            UriComponentsBuilder uriBuilder,
            @AuthenticationPrincipal UUID userId
            ){

        var like = likePostService.saveLike(req, userId);
        var uri = uriBuilder.path("/like-post/{userId}").buildAndExpand(userId).toUri();
        return ResponseEntity.created(uri).body(like);

    }

}
