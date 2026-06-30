package com.ivar.auth.modules.dto;

public record ApiKeyValidationResponse(
        boolean valid,
        String tenantId
) {
}