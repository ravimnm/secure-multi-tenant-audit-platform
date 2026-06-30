package com.ivar.audit.modules.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ivar.audit.modules.entity.RetentionPolicy;
import com.ivar.audit.modules.repository.RetentionPolicyRepository;

@RestController
@RequestMapping("/audit/retention")
public class RetentionController {

    private final
    RetentionPolicyRepository repository;

    public RetentionController(
            RetentionPolicyRepository repository) {

        this.repository = repository;
    }

    @PostMapping
    public RetentionPolicy create(
            @RequestBody
            RetentionPolicy policy) {

        return repository.save(policy);
    }

    @GetMapping("/{tenantId}")
    public RetentionPolicy get(
            @PathVariable String tenantId) {

        return repository
                .findByTenantId(tenantId);
    }
}