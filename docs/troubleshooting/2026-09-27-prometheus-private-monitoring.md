# Troubleshooting – 2026-09-27 – Prometheus private monitoring path

## Problem

After Oracle Cloud network hardening, Insurance metrics disappeared from Grafana.

Affected metrics:
- Insurance App API Latency (p95)
- Insurance JVM Heap
- Insurance JVM Heap Used vs Max

CPU and container memory metrics were also checked.

## Root cause

Booking Prometheus was scraping the Insurance VM through its **public IP**:

- `158.180.19.25:8080` – Insurance App
- `158.180.19.25:8082` – Insurance cAdvisor

After public access to ports `8080` and `8082` was blocked, these Prometheus targets stopped working.

## Fix

Prometheus was changed to use the Insurance VM's **private IP**:

```text
10.0.0.38:8080  → Insurance App
10.0.0.38:8082  → Insurance cAdvisor
```

OCI NSG and UFW were configured so that these ports are reachable from the Booking VM (`10.0.0.54`) while remaining inaccessible from the public Internet.

The change was committed to Git:

```text
8d53e9d Fix Insurance monitoring to use private IP
```

GitHub Actions deployment completed successfully.

## Verification

Private access from Booking VM:

```text
10.0.0.38:8080/actuator/prometheus → HTTP 200
10.0.0.38:8082/metrics             → HTTP 200
```

Prometheus container received the updated configuration.

Grafana subsequently showed Insurance App:
- CPU
- Container Memory
- API Latency (p95)
- JVM Heap
- JVM Heap Used vs Max

## Monitoring access

Important distinction:

- **Grafana** is accessed through NGINX: `/grafana/`
- **Prometheus** is accessed through NGINX: `/prometheus`
- **cAdvisor** is used by Prometheus for metrics collection and should remain an internal monitoring endpoint, not a public web endpoint.
- Insurance cAdvisor is reached by Booking Prometheus through the private OCI network: `10.0.0.38:8082`.

## Result

Monitoring works again while Insurance monitoring ports `8080` and `8082` are no longer publicly exposed.
