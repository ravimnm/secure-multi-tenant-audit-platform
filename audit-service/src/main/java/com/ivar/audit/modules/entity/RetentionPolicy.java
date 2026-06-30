package com.ivar.audit.modules.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "retention_policies")
public class RetentionPolicy {

    @Id
    @GeneratedValue(
            strategy =
            GenerationType.IDENTITY)
    private Long id;

    private String tenantId;

    private Integer retentionYears;

    private boolean archiveEnabled;

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

	public Integer getRetentionYears() {
		return retentionYears;
	}

	public void setRetentionYears(Integer retentionYears) {
		this.retentionYears = retentionYears;
	}

	public boolean isArchiveEnabled() {
		return archiveEnabled;
	}

	public void setArchiveEnabled(boolean archiveEnabled) {
		this.archiveEnabled = archiveEnabled;
	}

	@Override
	public String toString() {
		return "RetentionPolicy [id=" + id + ", tenantId=" + tenantId + ", retentionYears=" + retentionYears
				+ ", archiveEnabled=" + archiveEnabled + "]";
	}

	public RetentionPolicy(Long id, String tenantId, Integer retentionYears, boolean archiveEnabled) {
		super();
		this.id = id;
		this.tenantId = tenantId;
		this.retentionYears = retentionYears;
		this.archiveEnabled = archiveEnabled;
	}

	public RetentionPolicy() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}