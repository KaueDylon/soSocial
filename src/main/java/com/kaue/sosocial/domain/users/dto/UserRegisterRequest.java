package com.kaue.sosocial.domain.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UserRegisterRequest(
        @NotBlank  String name,
        @NotBlank @Email String email,
        @NotBlank @Size(min = 6) String password
) {}
