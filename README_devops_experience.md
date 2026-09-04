## 2026-07-15 – Docker & cAdvisor Monitoring Investigation

### EN

**Trigger:**
The issue appeared after updating Docker and cAdvisor. Before the
updates, the monitoring stack was running without the observed
performance impact.

**Problem:**
After the update, increased resource usage and latency were observed
on the resource-constrained Booking EasyApp production VM.

**Investigation:**
CPU, memory, network, disk, and disk I/O metrics were tested
incrementally. Resource usage was monitored using Docker statistics
and Grafana and compared with the InsuranceApp environment.

**Finding / Root Cause:**
The changed behaviour appeared after the Docker and cAdvisor updates.
Disk-related cAdvisor metrics had the most noticeable performance
impact on the Booking EasyApp VM.

**Action:**
Different cAdvisor metric configurations were tested to isolate the
source of the increased overhead and determine a suitable monitoring
configuration.

**Lesson Learned:**
Infrastructure and monitoring updates can change resource consumption
even when the application itself has not changed. Monitoring behaviour
should therefore be verified after Docker, cAdvisor, or other
infrastructure component updates.

### SK

**Spúšťač udalosti:**
Problém sa objavil po aktualizácii Dockeru a cAdvisor. Pred
aktualizáciami monitoring fungoval bez pozorovaného negatívneho
vplyvu na výkon.

**Problém:**
Po aktualizácii bola na produkčnom Booking EasyApp VM s obmedzenými
zdrojmi pozorovaná zvýšená spotreba prostriedkov a latencia.

**Analýza:**
Postupne boli testované CPU, memory, network, disk a disk I/O metriky.
Spotreba bola sledovaná pomocou Docker štatistík a Grafany a správanie
bolo porovnané s prostredím InsuranceApp.

**Zistenie / Príčina:**
Zmena správania sa objavila po aktualizácii Dockeru a cAdvisor.
Najvýraznejší vplyv na výkon Booking EasyApp VM mali diskové metriky
cAdvisor.

**Riešenie:**
Boli testované rôzne konfigurácie cAdvisor metrík s cieľom izolovať
zdroj zvýšenej záťaže a nájsť vhodnú konfiguráciu monitoringu.

**Poučenie:**
Aktualizácia infraštruktúry alebo monitoringu môže zmeniť spotrebu
systémových prostriedkov aj bez zmeny samotnej aplikácie. Po
aktualizácii Dockeru, cAdvisor alebo iných infraštruktúrnych komponentov
je preto vhodné overiť správanie monitoringu.

### 2026-09-02 — Booking & Insurance App: Automatic Docker Image Cleanup

Added automatic Docker image cleanup to the production deployment workflows.

After a successful application deployment, the VM now runs:

```bash
docker image prune -f
```

This removes unused dangling Docker images created by previous deployments
and prevents old application image layers from unnecessarily consuming disk space.

Booking production test:

Before cleanup:

Docker images: 24
Image storage: 6.455 GB
Reclaimable: 3.042 GB
VM disk usage: ~41%

After automatic cleanup:

Docker images: 6
Image storage: 2.988 GB
Reclaimable: 0 B
VM disk usage: ~33%

The cleanup removed old unused images while keeping all active application,
database, and monitoring containers running.

The same automatic cleanup approach is now being applied to the Insurance App production deployment.

===================================================================================================
