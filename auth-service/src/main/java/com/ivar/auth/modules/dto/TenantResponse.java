package com.ivar.auth.modules.dto;

import java.time.Instant;

public record TenantResponse(

        String tenantId,
        String companyName,
        String apiKey,
        boolean active,
        Instant createdAt

) {}