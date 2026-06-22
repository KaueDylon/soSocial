package com.kaue.sosocial.domain.users.auth.dto;

import java.util.UUID;

public record UserResponse(
        UUID id,
        String name
) {}
