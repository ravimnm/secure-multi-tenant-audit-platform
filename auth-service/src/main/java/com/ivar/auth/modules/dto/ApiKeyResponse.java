package com.ivar.auth.modules.dto;

public record ApiKeyResponse(
        String tenantId,
        boolean active
) {}