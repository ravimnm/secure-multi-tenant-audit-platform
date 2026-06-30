package com.ivar.audit.modules.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ivar.audit.modules.dto.TimelineEventResponse;
import com.ivar.audit.modules.service.TimelineService;


import java.time.Instant;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/audit/timeline")
public class TimelineController {

    private final TimelineService service;

    public TimelineController(
            TimelineService service) {

        this.service = service;
    }

    @GetMapping("/user/{userId}")
    public List<TimelineEventResponse>
    getUserTimeline(

            @PathVariable String userId) {

        return service.getUserTimeline(userId);
    }

    @GetMapping("/tenant/{tenantId}")
    public List<TimelineEventResponse>
    getTenantTimeline(

            @PathVariable String tenantId) {

        return service.getTenantTimeline(tenantId);
    }

    @GetMapping("/device/{deviceId}")
    public List<TimelineEventResponse>
    getDeviceTimeline(

            @PathVariable String deviceId) {

        return service.getDeviceTimeline(deviceId);
    }

    @GetMapping
    public List<TimelineEventResponse>
    getTimelineByTimeRange(

            @RequestParam
            @DateTimeFormat(
                    iso = DateTimeFormat.ISO.DATE_TIME)
            Instant start,

            @RequestParam
            @DateTimeFormat(
                    iso = DateTimeFormat.ISO.DATE_TIME)
            Instant end) {

        return service.getTimelineBetween(
                start,
                end);
    }
}