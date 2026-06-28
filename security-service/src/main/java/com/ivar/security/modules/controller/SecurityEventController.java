package com.ivar.security.modules.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.ivar.security.common.ApiResponse;
import com.ivar.security.modules.dto.SecurityEventRequest;
import com.ivar.security.modules.dto.SecurityEventResponse;
import com.ivar.security.modules.dto.SecurityVerificationResponse;
import com.ivar.security.modules.service.SecurityEventService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/security/events")
public class SecurityEventController {

    private final SecurityEventService service;

    public SecurityEventController(SecurityEventService service) {
        this.service = service;
    }

    @PostMapping
    public ApiResponse<SecurityEventResponse> createSecurityEvent(
            @Valid @RequestBody SecurityEventRequest request) {

        return new ApiResponse<>(
                service.createSecurityEvent(request)
        );
    }

    @GetMapping
    public ApiResponse<Page<SecurityEventResponse>> getEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "timestamp") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {
        System.out.println("CONTROLLER REACHED");

        return new ApiResponse<>(
                service.getEvents(page, size,sortBy, direction)
        );
    }

    @GetMapping("/verify")
    public ApiResponse<SecurityVerificationResponse> verifyChain() {

        return new ApiResponse<>(
                service.verifyChain()
        );
    }
}
