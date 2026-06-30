package com.ivar.security.modules.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ivar.security.modules.client.AuditClient;
import com.ivar.security.modules.dto.AuditLogDto;
import com.ivar.security.modules.entity.SecurityAlert;
import com.ivar.security.modules.repository.AlertRepository;
@Service
public class AlertEngineService {

    private final AuditClient auditClient;

    private final AlertRepository alertRepository;

    public AlertEngineService(
            AuditClient auditClient,
            AlertRepository alertRepository) {

        this.auditClient = auditClient;
        this.alertRepository = alertRepository;
    }

    @Scheduled(fixedRate = 60000)
    public void evaluateRules() {

        List<AuditLogDto> logs =
                auditClient.getRecentLogs();

        evaluateBruteForce(logs);

        evaluateMassExport(logs);

        evaluatePrivilegeEscalation(logs);
    }
    private void evaluateMassExport(
            List<AuditLogDto> logs) {

        Map<String, Long> exports =
                logs.stream()

                .filter(log ->
                        "CUSTOMER_DATA_EXPORTED"
                                .equals(log.getAction()))

                .collect(Collectors.groupingBy(
                        AuditLogDto::getActorId,
                        Collectors.counting()
                ));

        exports.forEach((user, count) -> {

            if (count >= 3) {

                createAlert(
                        user,
                        "MASS_EXPORT_ALERT",
                        "HIGH",
                        "Large amount of customer data exported"
                );
            }
        });
    }
    private void evaluateBruteForce(
            List<AuditLogDto> logs) {

        Map<String, Long> failures =
                logs.stream()

                .filter(log ->
                        "FAILED_LOGIN"
                                .equals(log.getAction()))

                .collect(Collectors.groupingBy(
                        AuditLogDto::getActorId,
                        Collectors.counting()
                ));

        failures.forEach((user, count) -> {

            if (count >= 5) {

                createAlert(
                        user,
                        "BRUTE_FORCE_ATTEMPT",
                        "HIGH",
                        "Multiple failed logins detected"
                );
            }
        });
    }
    private void evaluatePrivilegeEscalation(
            List<AuditLogDto> logs) {

        logs.stream()

                .filter(log ->
                        "ROLE_CHANGE"
                                .equals(log.getAction()))

                .forEach(log ->

                        createAlert(
                                log.getActorId(),
                                "PRIVILEGE_ESCALATION_ATTEMPT",
                                "CRITICAL",
                                "User role modification detected"
                        ));
    }
    private void createAlert(
            String actorId,
            String type,
            String severity,
            String description) {

        SecurityAlert alert =
                new SecurityAlert();

        alert.setActorId(actorId);

        alert.setAlertType(type);

        alert.setSeverity(severity);

        alert.setDescription(description);

        alert.setStatus("OPEN");

        alertRepository.save(alert);
    }
}