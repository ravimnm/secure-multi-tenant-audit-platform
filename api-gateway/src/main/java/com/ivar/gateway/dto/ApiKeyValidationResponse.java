package com.ivar.gateway.dto;

public record ApiKeyValidationResponse(
        boolean valid,
        String tenantId
) {}