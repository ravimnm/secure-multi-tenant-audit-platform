package com.ivar.audit.modules.dto;

import jakarta.validation.constraints.NotBlank;

public record AuditLogRequest(

        @NotBlank(message = "Action is required")
        String action

) {}