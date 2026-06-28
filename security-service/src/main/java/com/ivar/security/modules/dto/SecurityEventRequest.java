package com.ivar.security.modules.dto;

import com.ivar.security.modules.enums.EventSeverity;
import com.ivar.security.modules.enums.SecurityEventType;

public record SecurityEventRequest(
	    SecurityEventType eventType,
	    EventSeverity severity,
	    String source,
	    String details
	) {}
