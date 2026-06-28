package com.ivar.security.modules.dto;

import java.time.Instant;

import com.ivar.security.modules.enums.EventSeverity;
import com.ivar.security.modules.enums.SecurityEventType;

public record SecurityEventResponse(
	    Long id,
	    SecurityEventType eventType,
	    EventSeverity severity,
	    String source,
	    String details,
	    Instant timestamp
	) {}
