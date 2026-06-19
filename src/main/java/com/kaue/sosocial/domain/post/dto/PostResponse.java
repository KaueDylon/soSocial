package com.kaue.sosocial.domain.post.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record PostResponse(
        UUID id,
        String text,
        LocalDateTime createdAt
) {
}
