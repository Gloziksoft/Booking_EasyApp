# Booking VM – Security Hardening Points 1–4

**Date:** 2026-09-29
**Environment:** Booking VM (`booking-easyapp`)
**Scope:** Oracle Cloud network security, management access, container binding and SSH access

## Status

The following four security points from the review have been completed and verified:

- [x] **Point 1 – DNS & Accessibility**
- [x] **Point 2 – Oracle Cloud Security / public ports**
- [x] **Point 3 – Management and monitoring access**
- [x] **Point 4 – Container networking and host binding**

---

## Point 1 – DNS & Accessibility

The public DNS records were verified for the applications:

- `bookingapp.gloziksoft.sk` → Booking VM
- `insuranceapp.gloziksoft.sk` → Insurance VM

The Booking application remains accessible through its public HTTPS endpoint.

**Result:** Point 1 completed.

---

## Point 2 – Oracle Cloud Security / Public Ports

Public access to internal application, monitoring and management ports was audited and removed where it was not required.

The following services are no longer directly accessible from the Internet:

- Booking application `8080`
- cAdvisor `8081`
- pgAdmin `8082`
- phpAdmin `8084`
- Prometheus `9090`
- Grafana `3000`
- PostgreSQL `5432`

Only the required public web/SSH entry points remain exposed.

External testing confirmed that the service ports above time out from the Internet, while the public HTTPS application remains reachable.

**Result:** Point 2 completed.

---

## Point 3 – Management / Monitoring Access

The public NGINX routes for the following management services were removed:

- `/grafana/`
- `/prometheus/`
- `/pgadmin/`

Management and monitoring interfaces are accessed through SSH tunnels.

| Local address | Destination | Service |
|---|---|---|
| `http://localhost:13000` | Booking VM `127.0.0.1:3000` | Grafana |
| `http://localhost:19090` | Booking VM `127.0.0.1:9090` | Prometheus |
| `http://localhost:18081` | Booking VM `127.0.0.1:8081` | Booking cAdvisor |
| `http://localhost:18082/pgadmin/` | Booking VM `127.0.0.1:8082` | pgAdmin |
| `http://localhost:18083` | Insurance VM `10.0.0.38:8082` | Insurance cAdvisor |
| `http://localhost:18084` | Insurance VM `127.0.0.1:8084` | phpMyAdmin |

### Monitoring alias

```bash
alias monitoring='ssh -4   -L 13000:127.0.0.1:3000   -L 19090:127.0.0.1:9090   -L 18081:127.0.0.1:8081   -L 18082:127.0.0.1:8082   -L 18083:10.0.0.38:8082   ubuntu@152.70.22.237'
```

Usage:

```bash
monitoring

docker ps       → kontajnery
ss -ltnp        → porty
pgrep -af ssh   → SSH tunely
ss -ltnp | grep -E ':13000|:19090|:18081|:18082|:18083|:18084' → rýchla kontrola
pkill -f 'ssh.*-L 18084:127.0.0.1:8081' → Ak chceš ukončiť konkrétny 18084 tunel bez hľadania PID
pkill -f 'ssh.*-L' → A ak chceš ukončiť všetky SSH tunely vytvorené cez monitoring
```

This creates one SSH connection with all five local port forwards.

### Access

```text
localhost:13000  → Booking Grafana
localhost:19090  → Booking Prometheus
localhost:18081  → Booking cAdvisor
localhost:18082  → Booking pgAdmin
localhost:18083  → Insurance cAdvisor
localhost:18084  → Insurance phpMyAdmin
```

The `monitoring` alias was successfully tested.

**Result:** Point 3 completed.

---

## Point 4 – Container Networking & Binding

Production Docker Compose configurations were hardened so internal services are not published on all host interfaces.

### Booking application

```yaml
ports:
  - "127.0.0.1:8080:8080"
```

NGINX continues to access the application locally.

### PostgreSQL

The PostgreSQL `ports:` mapping was removed completely.

The application communicates with PostgreSQL through the Docker network:

```text
booking-app -> booking-db:5432
```

PostgreSQL is therefore not published to the host.

### pgAdmin

```yaml
ports:
  - "127.0.0.1:8082:80"
```

### Monitoring

```yaml
cAdvisor:
  - "127.0.0.1:8081:8080"

Prometheus:
  - "127.0.0.1:9090:9090"

Grafana:
  - "127.0.0.1:3000:3000"
```

Internal Docker communication remains:

```text
Prometheus -> app:8080
Prometheus -> cadvisor:8080
Booking app -> booking-db:5432
```

### Verification

After deployment:

```text
127.0.0.1:8080 -> Booking application
127.0.0.1:3000 -> Grafana
127.0.0.1:9090 -> Prometheus
127.0.0.1:8081 -> Booking cAdvisor
127.0.0.1:8082 -> pgAdmin
127.0.0.1:8084 -> phpMyAdmin
```

PostgreSQL has no host listener.

External tests confirmed that the application and management ports are not directly reachable from the Internet.

The public application remains available through:

```text
https://bookingapp.gloziksoft.sk/
```

**Result:** Point 4 completed.

---

## Current Access Model

```text
Internet
   |
   +---- HTTPS 443 ----> NGINX ----> 127.0.0.1:8080
   |                              |
   |                              +--> Booking Spring Boot
   |
   +---- SSH 22 ------> Booking VM
                            |
                            +--> SSH tunnels
                                  |
                                  +--> Grafana
                                  +--> Prometheus
                                  +--> Booking cAdvisor
                                  +--> pgAdmin
                                  +--> Insurance cAdvisor
```

Management and monitoring services are not intended to be directly exposed to the Internet.

---

## Final Status – 2026-09-29

**Point 1 – DNS & Accessibility:** COMPLETED
**Point 2 – Oracle Cloud Security / Public Ports:** COMPLETED
**Point 3 – Management / Monitoring Access:** COMPLETED
**Point 4 – Container Networking & Binding:** COMPLETED

## Next Step

**Point 5 – Input Validation**

This will be audited separately in the Booking application source code before the final review against all five points.
