package com.ivar.audit.modules.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ivar.audit.modules.dto.IntegrityReportDto;
import com.ivar.audit.modules.entity.AuditLog;
import com.ivar.audit.modules.repository.AuditLogRepository;

@Service
public class ReportService {

    private final AuditLogRepository repository;

    public ReportService(
    		AuditLogRepository repository) {

        this.repository = repository;
    }

    public List<AuditLog>
    userActivity(String userId) {

        return repository
                .findByActorId(userId);
    }

    public List<AuditLog>
    adminActivity() {

        return repository.findByActorRoleIn(
                List.of(
                        "ROLE_ADMIN",
                        "ROLE_SUPER_ADMIN"));
    }

    public List<AuditLog>
    dataAccess() {

        return repository.findByActionIn(
                List.of(
                        "CUSTOMER_VIEWED",
                        "CUSTOMER_UPDATED",
                        "CUSTOMER_DATA_EXPORTED"));
    }

	public IntegrityReportDto verifyIntegrity() {

	    List<AuditLog> logs =
	            repository.findAll();
	
	    long failures = 0;
	
	    for (int i = 1; i < logs.size(); i++) {
	
	        if (!logs.get(i)
	                .getPreviousHash()
	                .equals(
	                    logs.get(i - 1)
	                        .getHash())) {
	
	            failures++;
	        }
	    }
	
	    return new IntegrityReportDto(
	            failures == 0,
	            logs.size(),
	            failures
	    );
	}
	
}