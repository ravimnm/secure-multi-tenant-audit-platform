package com.ivar.security.modules.service;

import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.ivar.security.common.context.TenantContext;
import com.ivar.security.common.utils.HashUtil;
import com.ivar.security.modules.dto.SecurityEventRequest;
import com.ivar.security.modules.dto.SecurityEventResponse;
import com.ivar.security.modules.dto.SecurityVerificationResponse;
import com.ivar.security.modules.entity.SecurityEvent;
import com.ivar.security.modules.repository.SecurityEventRepository;

@Service
public class SecurityEventService {

    private final SecurityEventRepository repository;

    public SecurityEventService(SecurityEventRepository repository) {
        this.repository = repository;
    }

    public SecurityEventResponse createSecurityEvent(
            SecurityEventRequest request) {

        String tenantId = TenantContext.getTenant();

        if (tenantId == null) {
            throw new RuntimeException("Tenant not found");
        }

        SecurityEvent lastEvent =
                repository.findTopByTenantIdOrderByTimestampDesc(tenantId);

        String previousHash =
                lastEvent != null
                        ? lastEvent.getHash()
                        : "GENESIS";

        Instant now = Instant.now();

        String data =
                request.eventType().name()
                        + request.severity().name()
                        + request.source()
                        + request.details()
                        + tenantId
                        + now;

        String hash =
                HashUtil.sha256(previousHash + data);

        SecurityEvent event = new SecurityEvent();

        event.setTenantId(tenantId);
        event.setSource(request.source());
        event.setEventType(request.eventType());
        event.setSeverity(request.severity());
        event.setDetails(request.details());
        event.setTimestamp(now);
        event.setPreviousHash(previousHash);
        event.setHash(hash);

        SecurityEvent saved =
                repository.save(event);

        return new SecurityEventResponse(
                saved.getId(),
                saved.getEventType(),
                saved.getSeverity(),
                saved.getSource(),
                saved.getDetails(),
                saved.getTimestamp()
        );
    }

    public SecurityVerificationResponse verifyChain() {

        String tenantId = TenantContext.getTenant();

        if (tenantId == null) {
            throw new RuntimeException("Tenant not found");
        }

        List<SecurityEvent> events =
                repository.findByTenantIdOrderByTimestampAsc(tenantId);

        String previousHash = "GENESIS";

        for (SecurityEvent event : events) {

            String data =
                    event.getEventType().name()
                            + event.getSeverity().name()
                            + event.getSource()
                            + event.getDetails()
                            + event.getTenantId()
                            + event.getTimestamp();

            String recalculated =
                    HashUtil.sha256(previousHash + data);

            if (!recalculated.equals(event.getHash())) {

                return new SecurityVerificationResponse(
                        false,
                        "Tampering detected at ID "
                                + event.getId()
                );
            }

            previousHash = event.getHash();
        }

        return new SecurityVerificationResponse(
                true,
                "Chain valid"
        );
    }

    public Page<SecurityEventResponse> getEvents(
            int page,
            int size,
            String sortBy,
            String direction) {

        String tenantId = TenantContext.getTenant();

        if (tenantId == null) {
            throw new RuntimeException("Tenant not found");
        }

        Sort sort =
                direction.equalsIgnoreCase("desc")
                        ? Sort.by(sortBy).descending()
                        : Sort.by(sortBy).ascending();

        Pageable pageable =
                PageRequest.of(page, size, sort);

        return repository.findByTenantId(
                        tenantId,
                        pageable)
                .map(event ->
                        new SecurityEventResponse(
                                event.getId(),
                                event.getEventType(),
                                event.getSeverity(),
                                event.getSource(),
                                event.getDetails(),
                                event.getTimestamp()
                        ));
    }
}