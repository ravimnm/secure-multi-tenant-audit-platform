# 🔐 SMTAP — Secure Multi-Tenant Audit Platform

> **Cloud-native, tamper-evident audit logging and security monitoring platform for distributed enterprise systems.**

SMTAP is an enterprise-grade security platform designed to provide **secure audit trails, tenant isolation, integrity verification, and centralized security event monitoring** across microservices environments.

Modern organizations generate millions of security-relevant events every day. Traditional logging systems can store these events, but they rarely guarantee:

* Log integrity
* Tamper detection
* Tenant isolation
* Compliance readiness
* Centralized security visibility

SMTAP addresses these challenges by combining **cryptographic integrity mechanisms**, **multi-tenant security controls**, and **cloud-native microservices architecture**.

---

# ✨ Key Features

## 🔒 Secure Multi-Tenant Architecture

* Strong logical isolation between tenants
* Tenant-aware request processing
* Cross-tenant data access prevention
* JWT-based tenant context propagation

---

## 🛡 Tamper-Evident Audit Logging

Each audit event is protected using:

* SHA-256 cryptographic hashing
* Hash chaining
* Previous-hash linkage
* Integrity anchors

Any unauthorized modification invalidates the entire chain.

```text id="8wzbcb"
GENESIS
   ↓
Log1 → Hash1
   ↓
Log2 → Hash2
   ↓
Log3 → Hash3
```

---

## 📊 Centralized Security Monitoring

SMTAP aggregates security events from multiple services and presents them through a unified dashboard.

Examples:

* Authentication events
* Privilege escalations
* Runtime attacks
* Suspicious activities
* Security alerts

---

## 📈 Enterprise Dashboard

Interactive Angular dashboard provides:

* Audit metrics
* Security statistics
* Integrity score visualization
* Event trends
* Audit history

---

## 📑 Compliance Readiness

Export audit evidence for:

* ISO 27001
* SOC 2
* HIPAA
* PCI-DSS
* Internal audits

Supported export formats:

* CSV
* JSON (planned)

---

# 🏗 System Architecture

```text id="ihy28d"
                     +--------------------+
                     |     Frontend       |
                     |      Angular       |
                     +---------+----------+
                               |
                               v
                 +-------------------------+
                 |      API Gateway        |
                 | JWT Validation Routing  |
                 +-----------+-------------+
                             |
         +-------------------+-------------------+
         |                   |                   |
         v                   v                   v

+----------------+  +----------------+  +----------------+
| Auth Service   |  | Audit Service  |  | Security Svc  |
|----------------|  |----------------|  |----------------|
| Authentication |  | Audit Logging  |  | Event Mgmt    |
| User Mgmt      |  | Hash Chaining  |  | Alert Storage |
| Tenant Mgmt    |  | Verification   |  | Detection     |
+----------------+  +----------------+  +----------------+

         |                   |                   |
         +---------------------------------------+
                             |
                             v

                  +-------------------+
                  |   PostgreSQL DB   |
                  +-------------------+
```

---

# 🚀 Technology Stack

## Backend

| Technology           | Purpose                        |
| -------------------- | ------------------------------ |
| Java 21              | Core development               |
| Spring Boot          | Microservices framework        |
| Spring Security      | Authentication & authorization |
| Spring Cloud Gateway | API Gateway                    |
| Spring Data JPA      | Persistence layer              |
| JWT                  | Stateless authentication       |
| PostgreSQL           | Data storage                   |

---

## Frontend

| Technology | Purpose            |
| ---------- | ------------------ |
| Angular    | UI framework       |
| TypeScript | Frontend language  |
| Bootstrap  | Responsive UI      |
| Chart.js   | Data visualization |
| ng2-charts | Dashboard charts   |

---

## DevOps

| Technology | Purpose          |
| ---------- | ---------------- |
| Docker     | Containerization |
| Kubernetes | Orchestration    |
| Maven      | Build management |

---

# 🔐 Security Model

SMTAP enforces security at multiple layers.

## Authentication

Users authenticate through Auth Service and receive JWT access tokens.

## Authorization

Role-based access:

```text id="qqxst9"
ROLE_SUPER_ADMIN
ROLE_ADMIN
ROLE_AUDITOR
ROLE_USER
```

## Tenant Isolation

Every request carries tenant context.

```text id="aqgfcr"
Tenant A → Only Tenant A Data

Tenant B → Only Tenant B Data
```

Cross-tenant access is prohibited.

---

# 🧩 Microservices

| Service          | Port | Responsibilities               |
| ---------------- | ---- | ------------------------------ |
| API Gateway      | 8080 | Routing, JWT validation        |
| Auth Service     | 8082 | Authentication, users, tenants |
| Audit Service    | 8081 | Audit management & integrity   |
| Security Service | 8083 | Security event storage         |

---

# 📂 Project Structure

```text id="xwupzy"
smtap-microservices/

├── api-gateway/
├── auth-service/
├── audit-service/
├── security-service/
├── frontend/

├── kubernetes/
├── docker-compose.yml

└── README.md
```

---

# ⚙ Getting Started

## Prerequisites

* Java 21+
* Maven 3.9+
* Node.js 20+
* PostgreSQL
* Docker Desktop
* Kubernetes (Minikube/Kind)

---

## Clone Repository

```bash id="t6c17r"
git clone https://github.com/ravimnm/secure-multi-tenant-audit-platform.git

cd secure-multi-tenant-audit-platform
```

---

# 🗄 Database Setup

Create databases:

```sql id="1crxaj"
CREATE DATABASE auth_db;
CREATE DATABASE audit_db;
CREATE DATABASE security_db;
```

Update `application.properties` accordingly.

---

# 🔨 Build Services

```bash id="ceom47"
mvn clean package
```

Execute inside each microservice directory.

---

# ▶ Run Services

Start in the following order:

```text id="wfhuwz"
1. Auth Service
2. Audit Service
3. Security Service
4. API Gateway
5. Frontend
```

Backend:


```bash id="8e2n3o"
mvn spring-boot:run
```

Frontend:

```bash id="5m7w4f"
cd frontend

npm install

ng serve
```

Frontend URL:

```text id="5vn05e"
http://localhost:4200
```

---

# 📡 Core APIs

## Authentication

```http id="crd80e"
GET /auth/token
POST /auth/users
POST /auth/tenants
```

## Audit

```http id="v63l5g"
POST /audit/events
GET /audit/events
GET /audit/events/verify
GET /audit/events/export
```

## Security

```http id="m79t5n"
POST /security/events
GET /security/events
```

---

# 📸 Screenshots

Add screenshots here.

## Home Page

![Home](docs/images/home.png)

## Dashboard

![Dashboard](docs/images/dashboard.png)

## Audit Logs

![Audit Logs](docs/images/audit.png)


---

# 🎯 Use Cases

* Enterprise audit logging
* Compliance evidence generation
* SaaS multi-tenant applications
* Security monitoring
* Digital forensics support
* Incident response investigations

---

# 👨‍💻 Author

**Ravi Sankar Manem**

GitHub: https://github.com/ravimnm

---

# 📄 License

Licensed under the MIT License.
