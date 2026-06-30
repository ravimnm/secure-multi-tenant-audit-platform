package com.ivar.security.modules.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.ivar.security.modules.dto.AuditLogDto;

@FeignClient(
        name = "audit-service",
        url = "${audit.service.url}"
)
public interface AuditClient {

    @GetMapping("/audit/events/recent")
    List<AuditLogDto> getRecentLogs();
}
