package com.ivar.agent.entity;

public class AuditEvent {

    private String action;
    private String actorId;
    private String tenantId;

    public AuditEvent() {}

    public AuditEvent(
            String action,
            String actorId,
            String tenantId) {

        this.action = action;
        this.actorId = actorId;
        this.tenantId = tenantId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getActorId() {
        return actorId;
    }

    public void setActorId(String actorId) {
        this.actorId = actorId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}