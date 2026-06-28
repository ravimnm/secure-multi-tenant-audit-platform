package com.ivar.auth.modules.dto;

public record UserResponse(

        Long id,

        String username,

        String email,

        String role,

        String tenantId
) {
}