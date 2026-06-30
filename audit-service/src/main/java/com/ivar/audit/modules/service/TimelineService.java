package com.ivar.audit.modules.service;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ivar.audit.modules.dto.TimelineEventResponse;
import com.ivar.audit.modules.entity.*;
import com.ivar.audit.modules.repository.*;

@Service
public class TimelineService {

    private final AuditLogRepository repository;

    public TimelineService(
            AuditLogRepository repository) {

        this.repository = repository;
    }

    public List<TimelineEventResponse>
    getUserTimeline(String userId) {

        return repository
                .findByActorIdOrderByTimestampAsc(userId)
                .stream()
                .map(this::map)
                .toList();
    }

    public List<TimelineEventResponse>
    getTenantTimeline(String tenantId) {

        return repository
                .findByTenantIdOrderByTimestampAsc(tenantId)
                .stream()
                .map(this::map)
                .toList();
    }

    public List<TimelineEventResponse>
    getDeviceTimeline(String deviceId) {

        return repository
                .findByDeviceIdOrderByTimestampAsc(deviceId)
                .stream()
                .map(this::map)
                .toList();
    }

    public List<TimelineEventResponse>
    getTimelineBetween(
            Instant start,
            Instant end) {

        return repository
                .findByTimestampBetweenOrderByTimestampAsc(
                        start,
                        end)
                .stream()
                .map(this::map)
                .toList();
    }

    private TimelineEventResponse map(
            AuditLog log) {

        return new TimelineEventResponse(

                log.getId(),

                log.getActorId(),

                log.getTenantId(),

                log.getAction(),

                log.getActorRole(),

                log.getSource(),

                log.getDeviceId(),

                log.getIpAddress(),

                log.getTimestamp()
        );
    }
}