package com.ivar.security.modules.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ivar.security.modules.entity.SecurityEvent;

public interface SecurityEventRepository extends JpaRepository<SecurityEvent, Long> {
    SecurityEvent findTopByTenantIdOrderByTimestampDesc(String tenantId);
    List<SecurityEvent> findByTenantIdOrderByTimestampAsc(String tenantId);
    Page<SecurityEvent> findByTenantId(
            String tenantId,
            Pageable pageable);
}
