package com.ivar.audit.modules.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.time.Instant;

@Entity
@Table(name = "audit_logs")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String actorId;

    private String actorRole;

    private String tenantId;

    private String action;

    private String resource;

    
    private String source;      // WEB, API, AGENT

    private String deviceId;

    private String ipAddress;

    private String status;      // SUCCESS, FAILED

    
    private String correlationId;

    
    private String hash;

    private String previousHash;

    
    private Instant timestamp;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getActorId() {
		return actorId;
	}


	public void setActorId(String actorId) {
		this.actorId = actorId;
	}


	public String getActorRole() {
		return actorRole;
	}


	public void setActorRole(String actorRole) {
		this.actorRole = actorRole;
	}


	public String getTenantId() {
		return tenantId;
	}


	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}


	public String getAction() {
		return action;
	}


	public void setAction(String action) {
		this.action = action;
	}


	public String getResource() {
		return resource;
	}


	public void setResource(String resource) {
		this.resource = resource;
	}


	public String getSource() {
		return source;
	}


	public void setSource(String source) {
		this.source = source;
	}


	public String getDeviceId() {
		return deviceId;
	}


	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}


	public String getIpAddress() {
		return ipAddress;
	}


	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getCorrelationId() {
		return correlationId;
	}


	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}


	public String getHash() {
		return hash;
	}


	public void setHash(String hash) {
		this.hash = hash;
	}


	public String getPreviousHash() {
		return previousHash;
	}


	public void setPreviousHash(String previousHash) {
		this.previousHash = previousHash;
	}


	public Instant getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}


	@Override
	public String toString() {
		return "AuditLog [id=" + id + ", actorId=" + actorId + ", actorRole=" + actorRole + ", tenantId=" + tenantId
				+ ", action=" + action + ", resource=" + resource + ", source=" + source + ", deviceId=" + deviceId
				+ ", ipAddress=" + ipAddress + ", status=" + status + ", correlationId=" + correlationId + ", hash="
				+ hash + ", previousHash=" + previousHash + ", timestamp=" + timestamp + "]";
	}


	public AuditLog(Long id, String actorId, String actorRole, String tenantId, String action, String resource,
			String source, String deviceId, String ipAddress, String status, String correlationId, String hash,
			String previousHash, Instant timestamp) {
		super();
		this.id = id;
		this.actorId = actorId;
		this.actorRole = actorRole;
		this.tenantId = tenantId;
		this.action = action;
		this.resource = resource;
		this.source = source;
		this.deviceId = deviceId;
		this.ipAddress = ipAddress;
		this.status = status;
		this.correlationId = correlationId;
		this.hash = hash;
		this.previousHash = previousHash;
		this.timestamp = timestamp;
	}


	public AuditLog() {
		super();
		// TODO Auto-generated constructor stub
	}

    
}