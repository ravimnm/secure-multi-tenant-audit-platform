package com.ivar.audit.modules.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ivar.audit.modules.dto.IntegrityReportDto;
import com.ivar.audit.modules.entity.AuditLog;
import com.ivar.audit.modules.service.ReportService;

@RestController
@RequestMapping("/audit/reports")
public class ReportController {

    private final ReportService service;

    public ReportController(
            ReportService service) {

        this.service = service;
    }

    @GetMapping("/user-activity")
    public List<AuditLog> userActivity(
            @RequestParam String userId) {

        return service.userActivity(userId);
    }

    @GetMapping("/admin-activity")
    public List<AuditLog> adminActivity() {

        return service.adminActivity();
    }

    @GetMapping("/data-access")
    public List<AuditLog> dataAccess() {

        return service.dataAccess();
    }

    @GetMapping("/integrity")
    public IntegrityReportDto integrity() {

        return service.verifyIntegrity();
    }
}