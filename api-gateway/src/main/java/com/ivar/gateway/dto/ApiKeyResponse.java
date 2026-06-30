package com.ivar.gateway.dto;

public record ApiKeyResponse(
        String tenantId,
        boolean active
) {
}