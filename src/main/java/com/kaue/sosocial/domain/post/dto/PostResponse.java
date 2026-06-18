package com.kaue.sosocial.domain.post.dto;

import java.time.LocalDateTime;

public record PostResponse(
        String text,
        LocalDateTime createdAt
) {
}
