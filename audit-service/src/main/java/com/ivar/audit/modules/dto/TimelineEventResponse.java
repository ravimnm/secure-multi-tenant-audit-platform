package com.ivar.audit.modules.dto;


import java.time.Instant;

public record TimelineEventResponse(

        Long id,

        String actorId,

        String action,

        String resource,

        String status,

        String source,

        String deviceId,

        String ipAddress,

        Instant timestamp
) {}