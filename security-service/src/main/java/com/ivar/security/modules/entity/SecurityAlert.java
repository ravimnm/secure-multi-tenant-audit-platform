package com.ivar.security.modules.entity;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "security_alerts")
public class SecurityAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tenantId;

    private String actorId;

    private String alertType;

    private String severity;

    private String description;

    private String status; // OPEN, INVESTIGATING, RESOLVED

    private Instant createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = Instant.now();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getActorId() {
		return actorId;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public String getAlertType() {
		return alertType;
	}

	public void setAlertType(String alertType) {
		this.alertType = alertType;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "SecurityAlert [id=" + id + ", tenantId=" + tenantId + ", actorId=" + actorId + ", alertType="
				+ alertType + ", severity=" + severity + ", description=" + description + ", status=" + status
				+ ", createdAt=" + createdAt + "]";
	}

	public SecurityAlert(Long id, String tenantId, String actorId, String alertType, String severity,
			String description, String status, Instant createdAt) {
		super();
		this.id = id;
		this.tenantId = tenantId;
		this.actorId = actorId;
		this.alertType = alertType;
		this.severity = severity;
		this.description = description;
		this.status = status;
		this.createdAt = createdAt;
	}

	public SecurityAlert() {
		super();
		// TODO Auto-generated constructor stub
	}

    
}
