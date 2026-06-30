package com.ivar.audit.modules.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Instant; 
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ivar.audit.common.context.TenantContext;
import com.ivar.audit.common.context.UserContext;
import com.ivar.audit.common.response.PagedResponse;
import com.ivar.audit.common.utils.HashUtil;
import com.ivar.audit.modules.dto.AuditLogRequest;
import com.ivar.audit.modules.dto.AuditLogResponse;
import com.ivar.audit.modules.dto.AuditVerificationResponse;
import com.ivar.audit.modules.entity.AuditLog;
import com.ivar.audit.modules.repository.AuditLogRepository;
import com.ivar.audit.modules.repository.specification.AuditLogSpecification;

@Service
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public AuditLogService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public AuditLogResponse createAuditLog(AuditLogRequest req) {

    	String userId = UserContext.getUser();

    	if (userId == null) {
    	    userId = "system";
    	}

    	String role = UserContext.getRole();

    	if (role == null) {
    	    role = "ROLE_SYSTEM";
    	}

        String tenantId = TenantContext.getTenant();

        if (tenantId == null) {
            throw new RuntimeException("Tenant not found");
        }
        
//        String tenantId = TenantContext.getTenant();
//
//        if (tenantId == null) {
//            tenantId = req.tenantId();
//
//            if (tenantId == null) {
//                tenantId = "default-tenant";
//            }
//        }

        AuditLog lastLog =
                auditLogRepository
                        .findTopByTenantIdOrderByTimestampDesc(tenantId);

        String previousHash =
                lastLog != null
                        ? lastLog.getHash()
                        : "GENESIS";

        Instant now = Instant.now();

        String data =
                userId
                        + role
                        + tenantId
                        + req.action()
                        + now;

        String hash =
                HashUtil.sha256(previousHash + data);

        AuditLog auditLog = new AuditLog();

        auditLog.setActorId(userId);
        auditLog.setActorRole(role);
        auditLog.setTenantId(tenantId);
        auditLog.setAction(req.action());
        auditLog.setTimestamp(now);
        auditLog.setPreviousHash(previousHash);
        auditLog.setHash(hash);

        AuditLog savedLog =
                auditLogRepository.save(auditLog);

        try {
            Files.writeString(
                    Path.of("integrity_anchor.txt"),
                    savedLog.getHash(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );
        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to write integrity anchor",
                    e
            );
        }

        return new AuditLogResponse(
                savedLog.getId(),
                savedLog.getActorId(),
                savedLog.getActorRole(),
                savedLog.getTenantId(),
                savedLog.getAction(),
                savedLog.getTimestamp()
        );
    }

    public PagedResponse<AuditLogResponse> getLogs(
            String actorId,
            String action,
            Instant from,
            Instant to,
            int page,
            int size,
            String sortBy,
            String direction) {

//        String tenantId = TenantContext.getTenant();
//
//        if (tenantId == null) {
//            tenantId = "default-tenant";
//        }

    	String role = UserContext.getRole();
    	String tenantId = TenantContext.getTenant();

    	Specification<AuditLog> spec;

    	if ("ROLE_SUPER_ADMIN".equals(role)) {

    	    // no tenant restriction
    	    spec = Specification
    	            .where(AuditLogSpecification.byActor(actorId))
    	            .and(AuditLogSpecification.byAction(action))
    	            .and(AuditLogSpecification.byDateRange(from, to));

    	} else {

    	    if (tenantId == null) {
    	        throw new RuntimeException("Tenant not found");
    	    }

    	    spec = Specification
    	            .where(AuditLogSpecification.byTenant(tenantId))
    	            .and(AuditLogSpecification.byActor(actorId))
    	            .and(AuditLogSpecification.byAction(action))
    	            .and(AuditLogSpecification.byDateRange(from, to));
    	}

        Sort sort =
                direction.equalsIgnoreCase("asc")
                        ? Sort.by(sortBy).ascending()
                        : Sort.by(sortBy).descending();

        Pageable pageable =
                PageRequest.of(page, size, sort);

        Page<AuditLog> result =
                auditLogRepository.findAll(spec, pageable);

        List<AuditLogResponse> data =
                result.getContent()
                        .stream()
                        .map(log -> new AuditLogResponse(
                                log.getId(),
                                log.getActorId(),
                                log.getActorRole(),
                                log.getTenantId(),
                                log.getAction(),
                                log.getTimestamp()
                        ))
                        .toList();

        return new PagedResponse<>(
                data,
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages(),
                result.hasNext()
        );
    }

    public AuditVerificationResponse verifyChain() {

//        String tenantId = TenantContext.getTenant();
//
//        if (tenantId == null) {
//            tenantId = "default-tenant";
//        }
    	String tenantId = TenantContext.getTenant();

    	if (tenantId == null) {
    	    throw new RuntimeException("Tenant not found");
    	}

        List<AuditLog> logs =
                auditLogRepository
                        .findByTenantIdOrderByTimestampAsc(tenantId);

        if (logs.isEmpty()) {
            return new AuditVerificationResponse(
                    true,
                    "No logs to verify"
            );
        }

        String previousHash = "GENESIS";

        for (AuditLog log : logs) {

            String data =
                    log.getActorId()
                            + log.getActorRole()
                            + log.getTenantId()
                            + log.getAction()
                            + log.getTimestamp();

            String recalculatedHash =
                    HashUtil.sha256(previousHash + data);

            if (!recalculatedHash.equals(log.getHash())) {

                System.out.println(
                        "ALERT: Tampering detected at log "
                                + log.getId());

                return new AuditVerificationResponse(
                        false,
                        "Tampering detected at log ID: "
                                + log.getId()
                );
            }

            previousHash = log.getHash();
        }

        try {

            String anchorHash =
                    Files.readString(
                            Path.of("integrity_anchor.txt"))
                            .trim();

            AuditLog lastLog =
                    logs.get(logs.size() - 1);

            if (!lastLog.getHash().equals(anchorHash)) {

                System.out.println(
                        "ALERT: Anchor mismatch detected");

                return new AuditVerificationResponse(
                        false,
                        "Anchor mismatch: possible chain rewrite attack"
                );
            }

        } catch (IOException e) {

            return new AuditVerificationResponse(
                    false,
                    "Failed to read integrity anchor"
            );
        }

        return new AuditVerificationResponse(
                true,
                "Audit log is valid"
        );
    }

    public String exportLogsToCSV() {

//        String tenantId = TenantContext.getTenant();
//
//        if (tenantId == null) {
//            tenantId = "default-tenant";
//        }
    	String tenantId = TenantContext.getTenant();

    	if (tenantId == null) {
    	    throw new RuntimeException("Tenant not found");
    	}

        List<AuditLog> logs =
                auditLogRepository
                        .findByTenantIdOrderByTimestampAsc(tenantId);

        StringBuilder csv = new StringBuilder();

        csv.append(
                "id,actorId,role,tenantId,action,timestamp\n");

        for (AuditLog log : logs) {

            csv.append(log.getId()).append(",")
                    .append(log.getActorId()).append(",")
                    .append(log.getActorRole()).append(",")
                    .append(log.getTenantId()).append(",")
                    .append(log.getAction()).append(",")
                    .append(log.getTimestamp())
                    .append("\n");
        }

        return csv.toString();
    }
}

