package com.ivar.audit.modules.service;

import com.ivar.audit.modules.entity.AuditLog;
import com.ivar.audit.modules.entity.RetentionPolicy;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ivar.audit.modules.repository.AuditLogRepository;
import com.ivar.audit.modules.repository.RetentionPolicyRepository;


@Service
public class RetentionService {

    private final AuditLogRepository auditRepository;
    private final RetentionPolicyRepository policyRepository;

    public RetentionService(
            AuditLogRepository auditRepository,
            RetentionPolicyRepository policyRepository) {

        this.auditRepository = auditRepository;
        this.policyRepository = policyRepository;
    }

    @Scheduled(cron = "0 0 1 * * *")
    public void cleanup() {

        System.out.println("Retention cleanup running...");

        List<RetentionPolicy> policies =
                policyRepository.findAll();

        for (RetentionPolicy policy : policies) {

            Instant cutoff =
                    Instant.now()
                            .minus(
                                    policy.getRetentionYears(),
                                    ChronoUnit.YEARS);

            long deleted =
                    auditRepository
                            .deleteByTenantIdAndTimestampBefore(
                                    policy.getTenantId(),
                                    cutoff);

            System.out.println(
                    "Deleted " + deleted +
                    " logs for tenant " +
                    policy.getTenantId());
        }
    }
}