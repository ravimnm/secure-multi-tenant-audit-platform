package com.ivar.auth.modules.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ivar.auth.modules.entity.Tenant;

public interface TenantRepository
        extends JpaRepository<Tenant, Long> {

    Optional<Tenant> findByTenantId(String tenantId);

    boolean existsByTenantId(String tenantId);
    Optional<Tenant> findByApiKey(String apiKey);
}
