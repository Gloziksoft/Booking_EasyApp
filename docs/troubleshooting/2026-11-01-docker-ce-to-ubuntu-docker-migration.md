# Booking – Docker Migration & Infrastructure Troubleshooting

**Date:** 2026-11-01  
**Host:** `booking-easyapp`  
**OS:** Ubuntu 24.04 LTS  
**Status:** Completed

## 1. Docker CE to Ubuntu Docker Migration

### Objective

Replace Docker CE from the official Docker repository with Ubuntu's `docker.io` package to standardize Docker installation across Ubuntu VMs.

### Previous installation

- Docker Engine 29.9.2
- containerd.io 2.4.2
- Docker Compose plugin 5.6.1

### New installation

- `docker.io` 29.1.3
- `docker-compose-v2` 2.40.3
- `containerd` 2.2.1
- `runc` 1.3.4

### Migration procedure

1. Created and verified a PostgreSQL cluster backup using `pg_dumpall`.
2. Removed Docker CE packages and installed Ubuntu Docker packages.
3. Enabled and started Docker service.
4. Verified existing containers and volumes.
5. Removed obsolete APT packages using `apt autoremove`.

No Docker volumes or application data were intentionally deleted.

### Post-migration verification

All six containers were running:

| Container | Status |
|---|---|
| booking-app | Up |
| booking-db | Up |
| cadvisor | Up (healthy) |
| prometheus | Up |
| grafana | Up |
| pgadmin | Up |

Existing Docker volumes were preserved: `booking-data`, `grafana-data`, `prometheus-data` and the existing anonymous volume.

## 2. cAdvisor & Grafana Troubleshooting

After migration, Grafana's Container CPU and Container Memory panels displayed `No data`.

- Investigated cAdvisor metrics and logs; found a Docker API compatibility error (`client 1.41`, minimum supported API `1.44`).
- Compared cAdvisor configuration with the working Insurance VM.
- Removed custom cAdvisor command arguments.
- Changed `privileged` to `false` and `/var/run` mount to read-write.
- Preserved the image (`v0.49.1`), port and Docker network.

**Result:** Deployment successful. CPU and Container Memory metrics were restored in Grafana.

## 3. K3s – Booking Database Troubleshooting

Investigated repeated restarts of the Booking application and PostgreSQL pods:

- `booking-app-deployment` – repeated restarts.
- `postgresql-booking-app-0` – repeated restarts.
- PostgreSQL logs reported `database "booking" does not exist`.
- Found a schema mismatch involving the missing `email_verified` column.
- Compared the database structure with the application configuration: `booking_db`, Flyway migrations and Hibernate `validate`.
- Created a backup before database troubleshooting.
- Located the local production database dump and prepared a PostgreSQL SQL dump for restoration.

**Result:** PostgreSQL dump generation completed successfully. Final database restoration and application verification should be documented separately once confirmed.

## 4. Cleanup & Result

- Preserved Docker volumes, application files, PostgreSQL data, configuration and secrets.
- Confirmed the temporary SQL backup directory was no longer present during cleanup.
- Docker CE was successfully replaced with Ubuntu `docker.io`.
- All six Docker containers remained operational.
- Grafana CPU and RAM monitoring was restored.
- K3s database troubleshooting and dump preparation were performed.

**Note:** Docker Engine was downgraded from 29.9.2 to 29.1.3 during package-source standardization.
