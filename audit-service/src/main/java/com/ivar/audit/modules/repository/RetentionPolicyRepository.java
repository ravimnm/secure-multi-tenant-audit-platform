package com.ivar.audit.modules.repository;

import java.time.Instant;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ivar.audit.modules.entity.RetentionPolicy;

import jakarta.transaction.Transactional;

public interface RetentionPolicyRepository
        extends JpaRepository<RetentionPolicy, Long> {

	RetentionPolicy findByTenantId(String tenantId);

}