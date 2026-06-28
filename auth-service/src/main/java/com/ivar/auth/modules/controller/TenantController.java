package com.ivar.auth.modules.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.ivar.auth.modules.dto.TenantRequest;
import com.ivar.auth.modules.dto.TenantResponse;
import com.ivar.auth.modules.service.TenantService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth/tenants")
public class TenantController {

    private final TenantService service;

    public TenantController(
            TenantService service) {

        this.service = service;
    }

    @PostMapping
    public TenantResponse createTenant(

            @Valid
            @RequestBody
            TenantRequest request) {

        return service.createTenant(request);
    }

    @GetMapping
    public List<TenantResponse> getAllTenants() {

        return service.getAllTenants();
    }
    
    @PostMapping("/bulk")
    public List<TenantResponse> createTenants(
            @RequestBody List<TenantRequest> requests) {

        return requests.stream()
                .map(service::createTenant)
                .toList();
    }
}