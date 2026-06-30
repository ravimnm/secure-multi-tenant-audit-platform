# SMTAP - Secure Multi-Tenant Audit Platform

SMTAP (Secure Multi-Tenant Audit Platform) is an audit platform designed for enterprise SaaS environments. The platform provides centralized audit logging, tamper-evident storage, security event monitoring, investigation timelines, and compliance reporting.

## Features

### Audit Management
- Centralized audit log collection
- Multi-tenant audit isolation
- Dynamic audit log search and filtering
- Audit log export (CSV)
- Recent activity monitoring

### Tamper Detection
- Hash-chained audit logs
- Integrity verification engine
- Integrity anchor generation
- Chain validation and tampering detection

### Security Monitoring
- Security event collection
- Security alert management
- Severity classification
- User risk visualization
- Open alert tracking

### Investigation & Forensics
- User investigation timeline
- Tenant activity timeline
- Device-based timeline reconstruction
- Recent audit event tracking

### Compliance Reporting
- User activity reports
- Admin activity reports
- Data access reports
- Integrity verification reports

### Dashboard Analytics
- Audit KPIs
- Monthly audit trends
- Severity distribution charts
- Recent activity widgets
- Risk score visualization

---

# Architecture

The platform follows a microservices architecture.

```text
                +----------------+
                |    Frontend    |
                | Angular 20     |
                +--------+-------+
                         |
                         v
                +----------------+
                |  API Gateway   |
                | Spring Cloud   |
                +--------+-------+
                         |
         ---------------------------------
         |                               |
         v                               v
+----------------+           +----------------------+
| Auth Service   |           | Audit Service        |
| JWT / RBAC     |           | Audit Logs           |
+----------------+           | Timeline Engine      |
                             | Integrity Engine     |
                             +----------+-----------+
                                        |
                                        v
                             +----------------------+
                             | Security Service     |
                             | Alerts & Events      |
                             +----------------------+
```

---

# Tech Stack

## Backend

- Java 21
- Spring Boot 3
- Spring Security
- Spring Data JPA
- PostgreSQL
- JWT Authentication
- Maven

## Frontend

- Angular 20
- TypeScript
- Bootstrap 5
- Chart.js
- ng2-charts

## Database

- PostgreSQL

---

# Security Features

## Tamper-Evident Audit Logs

Each audit event is cryptographically linked to the previous event.

```text
GENESIS
   ↓
Log1 → Hash1
   ↓
Log2 → Hash2
   ↓
Log3 → Hash3
```

Any modification breaks the chain and is detected during integrity verification.

## Multi-Tenant Isolation

Audit and security data are logically isolated using tenant identifiers.

## Integrity Anchor

The latest audit hash is persisted as an integrity anchor for detecting chain rewrite attacks.

---

# Modules

## Audit Service

Responsible for:

- Audit log ingestion
- Log querying
- Integrity verification
- CSV export
- Timeline generation
- Compliance reporting

## Security Service

Responsible for:

- Security event ingestion
- Alert generation
- Alert management
- Severity analysis

---

# REST APIs

## Audit APIs

| Method | Endpoint | Description |
|---------|-----------|-------------|
| POST | `/audit/events` | Create audit event |
| GET | `/audit/events` | Get audit logs |
| GET | `/audit/events/verify` | Verify integrity |
| GET | `/audit/events/export` | Export logs |
| GET | `/audit/events/recent` | Recent events |

## Timeline APIs

| Method | Endpoint |
|---------|-----------|
| GET | `/audit/timeline/user/{userId}` |
| GET | `/audit/timeline/tenant/{tenantId}` |
| GET | `/audit/timeline/device/{deviceId}` |

## Security APIs

| Method | Endpoint |
|---------|-----------|
| POST | `/security/events` |
| GET | `/security/events` |
| GET | `/security/events/verify` |

---

# UI Preview

## Dashboard

- KPI cards
- Audit statistics
- Security metrics
- Investigation timeline
- Severity distribution
- Recent audit events

## Audit Logs

- Search by actor
- Search by action
- Compliance reports
- Audit history table

## Security Events

- Security alerts
- Risk score visualization
- Security event table

---

# Future Enhancements

- Redis caching
- Kafka-based event streaming
- Elasticsearch integration
- SIEM integration
- Real-time notifications
- Rule engine
- Docker deployment
- Kubernetes deployment
- Role-based dashboards
- Threat intelligence integration

---

# Running the Project

## Backend

```bash
mvn clean install
mvn spring-boot:run
```

## Frontend

```bash
npm install
ng serve
```

Frontend runs at:

```text
http://localhost:4200
```

---

# Author

**Ravi Sankar Manem**

- 💻 GitHub: https://github.com/ravimnm

---
