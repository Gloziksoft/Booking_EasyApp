# Central Monitoring Migration -- Toolnest

**Started:** 2026-11-02
**Last updated:** 2026-11-07
**Hosts:** Toolnest `10.0.0.77` · Booking `10.0.0.54` · Insurance `10.0.0.38`
**OS:** Ubuntu 24.04 LTS

## 1. Objective

Centralize Prometheus and Grafana on Toolnest to monitor Booking,
Insurance and Toolnest. Keep access private and retain Booking's
existing monitoring until migration is verified.

## 2. Remote Metrics

Booking and Insurance expose application and cAdvisor metrics to
the central Prometheus instance.

Verified endpoints:

- Booking: `10.0.0.54:8080/actuator/prometheus`
- Booking: `10.0.0.54:8081/metrics`
- Insurance: `10.0.0.38:8080/actuator/prometheus`
- Insurance: `10.0.0.38:8082/metrics`

All four endpoints returned HTTP 200 from Toolnest.

## 3. Toolnest -- Central Monitoring Stack

Monitoring runs from:

`/opt/monitoring/docker-compose.monitoring.yml`

Services:

- Prometheus `v2.51.2` (`127.0.0.1:9090`)
- Grafana `10.4.2` (`127.0.0.1:3000`)
- cAdvisor `v0.49.1`
- Node Exporter `v1.8.2`

Prometheus and Grafana are bound to localhost. Exporters are
scraped over the internal Docker network.

## 4. Prometheus Configuration

Prometheus has six scrape jobs:

| Job | Target |
|---|---|
| `booking-app` | `10.0.0.54:8080` (`/actuator/prometheus`) |
| `booking-cadvisor` | `10.0.0.54:8081` |
| `insurance-app` | `10.0.0.38:8080` (`/actuator/prometheus`) |
| `insurance-cadvisor` | `10.0.0.38:8082` |
| `toolnest-cadvisor` | `cadvisor:8080` |
| `toolnest-node` | `node-exporter:9100` |

Configuration was validated with `promtool check config`.

**Verification: all 6/6 targets UP.**

## 5. Grafana Dashboard

Imported the Booking application monitoring dashboard into central
Grafana.

Current dashboard:

`Toolnest – Central Monitoring`

Verified panels for Booking and Insurance are displaying metrics,
including CPU, container memory, API latency and JVM metrics.

The dashboard currently contains both applications. Separate
Booking and Insurance dashboards have not been created.

Central Grafana is accessible through an SSH tunnel.

## 6. Remaining Work and Safety

1. Add Toolnest infrastructure panels (host CPU, RAM, disk, network
   and container metrics).
2. Verify the complete central monitoring dashboard.
3. Update the local SSH tunnel to use Toolnest Grafana and Prometheus.
4. Keep Booking Prometheus and Grafana until their removal is
   explicitly approved.
5. **Keep Booking cAdvisor running** because central Prometheus
   still scrapes it.
6. Remove obsolete Booking monitoring configuration and services
   only after verification and approval.

**No old Booking monitoring services or configuration files have
been removed.**
