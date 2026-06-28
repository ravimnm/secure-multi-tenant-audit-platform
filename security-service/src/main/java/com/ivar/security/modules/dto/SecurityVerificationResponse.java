package com.ivar.security.modules.dto;

public record SecurityVerificationResponse(
        boolean valid,
        String message
) {}
