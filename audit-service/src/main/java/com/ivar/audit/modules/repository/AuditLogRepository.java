package com.ivar.audit.modules.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ivar.audit.modules.entity.AuditLog;
import com.ivar.audit.modules.dto.AuditLogResponse;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long>,JpaSpecificationExecutor<AuditLog> {
	List<AuditLog> findByTenantId(String tenantId);
	AuditLog findTopByTenantIdOrderByTimestampDesc(String tenantId);
	List<AuditLog> findByTenantIdOrderByTimestampAsc(String tenantId);
	List<AuditLog> findByDeviceIdOrderByTimestampAsc(
            String deviceId);
	List<AuditLog> findByActorIdOrderByTimestampAsc(String userId);

    List<AuditLog> findByTimestampBetweenOrderByTimestampAsc(
            Instant start,
            Instant end
    );
	List<AuditLog> findByTimestampAfter(Instant cutoff);
	List<AuditLog>
	findByActorId(String actorId);

	List<AuditLog>
	findByActorRoleIn(
	        List<String> roles);

	List<AuditLog>
	findByActionIn(
	        List<String> actions);
	long deleteByTenantIdAndTimestampBefore(String tenantId, Instant cutoff);
	List<AuditLog> findByTenantIdAndTimestampBefore(String tenantId, Instant cutoff);
}
