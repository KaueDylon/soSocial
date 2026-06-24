package com.kaue.sosocial.domain.like_post.dto;

import com.kaue.sosocial.domain.post.entity.Post;
import com.kaue.sosocial.domain.users.entity.User;

import java.time.LocalDateTime;
import java.util.UUID;

public record LikePostResponse(
        UUID post,
        UUID user,
        LocalDateTime createAt
) {
}
