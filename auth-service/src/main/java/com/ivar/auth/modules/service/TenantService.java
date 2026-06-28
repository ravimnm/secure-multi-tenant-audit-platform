package com.ivar.auth.modules.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ivar.auth.modules.dto.TenantRequest;
import com.ivar.auth.modules.dto.TenantResponse;
import com.ivar.auth.modules.entity.Tenant;
import com.ivar.auth.modules.repository.TenantRepository;

@Service
public class TenantService {

    private final TenantRepository repository;

    public TenantService(
            TenantRepository repository) {

        this.repository = repository;
    }

    public TenantResponse createTenant(
            TenantRequest request) {

        if (repository.existsByTenantId(
                request.tenantId())) {

            throw new RuntimeException(
                    "Tenant already exists");
        }

        Tenant tenant = new Tenant();

        tenant.setTenantId(
                request.tenantId());

        tenant.setCompanyName(
                request.companyName());

        tenant.setApiKey(
                UUID.randomUUID().toString());

        Tenant saved =
                repository.save(tenant);

        return map(saved);
    }

    public List<TenantResponse> getAllTenants() {

        return repository.findAll()
                .stream()
                .map(this::map)
                .toList();
    }

    private TenantResponse map(Tenant tenant) {

        return new TenantResponse(
                tenant.getTenantId(),
                tenant.getCompanyName(),
                tenant.getApiKey(),
                tenant.isActive(),
                tenant.getCreatedAt()
        );
    }
    public List<TenantResponse> createUsers(
            List<TenantRequest> requests) {

        return requests.stream()
                .map(this::createTenant)
                .toList();
    }
}