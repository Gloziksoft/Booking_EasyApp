# Booking VM – Security Hardening – Point 3

**Date:** 2026-09-28
**Scope:** Booking VM (`bookingapp.gloziksoft.sk`)
**Topic:** Database & management dashboards – access restriction

## Problem

Management and monitoring services were previously reachable through host ports:

- Grafana – `3000`
- Prometheus – `9090`
- pgAdmin – `8082`

These services should not be publicly exposed.

## Changes

Public access through NGINX was removed for:

- `/grafana/`
- `/prometheus/`
- `/pgadmin/`

The corresponding OCI public ingress rules had already been removed during Point 2.

The new administration/monitoring access is through **SSH tunnels**:

```text
Local PC                     Booking VM
localhost:13000  ──────────> 127.0.0.1:3000   Grafana
localhost:19090  ──────────> 127.0.0.1:9090   Prometheus
localhost:18082  ──────────> 127.0.0.1:8082   pgAdmin
```

Grafana was also configured for direct access through the SSH tunnel:

```text
GF_SERVER_ROOT_URL=http://localhost:13000/
GF_SERVER_SERVE_FROM_SUB_PATH=false
```

The CI/CD workflow was **not changed**.

## Verification

### Public access

Direct public access to the management ports was tested and is blocked:

```text
:3000  -> timeout
:9090  -> timeout
:8082  -> timeout
```

The public HTTPS application remains available:

```text
https://bookingapp.gloziksoft.sk/ -> HTTP 200
```

Requests to the old NGINX paths are now handled by the Spring Boot application rather than exposing the management services.

### SSH tunnel access

The following were successfully tested:

```text
Grafana    localhost:13000 -> VM 127.0.0.1:3000
Prometheus localhost:19090 -> VM 127.0.0.1:9090
pgAdmin    localhost:18082 -> VM 127.0.0.1:8082
```

Grafana is accessible through the tunnel and Prometheus shows all four configured targets as **UP**.

## Result

The Booking VM now exposes the public application through HTTPS only.
Grafana, Prometheus and pgAdmin are no longer intended to be publicly accessible and are administered through SSH tunnels.

**Point 3 completed.**

**Next:** Point 4 – container port binding and network exposure audit.
