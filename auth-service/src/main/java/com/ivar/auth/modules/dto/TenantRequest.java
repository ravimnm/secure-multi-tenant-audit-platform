package com.ivar.auth.modules.dto;

import jakarta.validation.constraints.NotBlank;

public record TenantRequest(

        @NotBlank
        String tenantId,

        @NotBlank
        String companyName

) {}