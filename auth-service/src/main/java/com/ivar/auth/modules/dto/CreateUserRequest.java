package com.ivar.auth.modules.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(

        @NotBlank
        String username,

        @NotBlank
        String password,

        @NotBlank
        String email,

        @NotBlank
        String role,

        @NotBlank
        String tenantId
) {
}