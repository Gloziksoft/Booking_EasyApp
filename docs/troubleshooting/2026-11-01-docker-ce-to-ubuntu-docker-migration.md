# Docker CE to Ubuntu Docker Migration

**Date:** 2026-11-01  
**Host:** `booking-easyapp`  
**OS:** Ubuntu 24.04 LTS  
**Status:** Completed

## Objective

Replace Docker CE from the official Docker repository with Ubuntu's `docker.io` package to standardize Docker installation across the Ubuntu VMs.

## Previous installation

Docker CE packages:
- Docker Engine 29.9.2
- containerd.io 2.4.2
- Docker Compose plugin 5.6.1

## New installation

Packages installed from Ubuntu APT:
- `docker.io` 29.1.3
- `docker-compose-v2` 2.40.3
- `containerd` 2.2.1
- `runc` 1.3.4

Docker service enabled and running:

```bash
sudo systemctl enable --now docker
sudo systemctl status docker --no-pager
```

## Migration procedure

1. Created a PostgreSQL cluster dump using `pg_dumpall`.
2. Verified the compressed dump using `gzip -t`.
3. Removed Docker CE packages:
   - `docker-ce`
   - `docker-ce-cli`
   - `docker-ce-rootless-extras`
   - `docker-buildx-plugin`
   - `docker-compose-plugin`
   - `containerd.io`
4. Installed Ubuntu Docker and Compose V2.
5. Enabled and started the Docker service.
6. Verified that existing containers and volumes were available.
7. Removed obsolete automatically installed APT packages using `apt autoremove`.

No Docker volumes or application data were intentionally deleted.

## Post-migration verification

All six existing containers were running:

| Container | Status |
|---|---|
| booking-app | Up |
| booking-db | Up |
| cadvisor | Up (healthy) |
| prometheus | Up |
| grafana | Up |
| pgadmin | Up |

Existing Docker volumes were preserved:

- `booking-data`
- `grafana-data`
- `prometheus-data`
- Existing anonymous volume

The Docker service started successfully and restored the existing containers.

APT maintenance:
- `apt update` completed successfully.
- `apt upgrade -s` reported no pending upgrades.
- Four obsolete packages were removed.

## Cleanup

The temporary SQL backup directory `~/booking-backup/` was no longer present during the final cleanup check.

No cleanup was performed on:
- `/var/lib/docker`
- Docker volumes
- Booking application files
- PostgreSQL data
- Production configuration and secrets

## Result

Docker CE was successfully replaced with Ubuntu `docker.io`.

All six existing containers were running after the migration, and the existing Docker volumes were preserved.

**Note:** Docker Engine was downgraded from 29.9.2 to 29.1.3 as part of the package-source standardization. The migration confirms successful container restoration, but does not by itself establish a performance improvement.
