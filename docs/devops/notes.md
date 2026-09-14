# DevOps Notes

General DevOps and deployment notes for BookingEasyApp.

These notes describe the infrastructure and deployment approach used
during development and production-like operation.

---

## Docker

Docker is used to containerize the Spring Boot application and its
supporting services.

The project uses Docker Compose for multi-container environments.

Typical services include:

- Spring Boot application
- PostgreSQL
- pgAdmin
- Prometheus
- Grafana
- cAdvisor

Application and monitoring stacks are kept separate.

---

## Docker Compose

Docker Compose is used to define and manage multiple containers.

The project uses separate Compose configurations for:

- development
- application production runtime
- monitoring

This makes it possible to start and manage different parts of the
environment independently.

---

## Linux / VM

The production-like environment runs on a Linux virtual machine.

The VM is treated as a runtime environment.

The Git repository is the source of truth for application and
deployment configuration.

---

## CI/CD

GitHub Actions is used for the application deployment pipeline.

The general flow is:

    Git push
        ↓
    GitHub Actions
        ↓
    Maven build
        ↓
    Docker image
        ↓
    GitHub Container Registry
        ↓
    SSH deployment
        ↓
    Docker Compose
        ↓
    Running application

---

## Container Registry

Docker images are stored in GitHub Container Registry (GHCR).

Images use both commit-based tags and the production tag.

Example:

    ghcr.io/gloziksoft/booking-app:<commit-sha>
    ghcr.io/gloziksoft/booking-app:prod

---

## Monitoring

Application and infrastructure monitoring uses:

- Prometheus
- Grafana
- cAdvisor

Important metrics include:

- CPU usage
- memory usage
- container resource usage
- application latency
- JVM-related metrics

Monitoring is also used during troubleshooting to compare system
behaviour before and after infrastructure changes.

---

## Deployment Principles

Configuration changes should be made in the repository rather than
directly on the production VM.

The general principle is:

    Change repository
        ↓
    Git commit
        ↓
    Git push
        ↓
    CI/CD
        ↓
    New Docker image
        ↓
    Deployment

The VM is the runtime environment, not the source of truth.

---

## Troubleshooting Approach

Infrastructure problems are investigated step by step.

Typical checks include:

- application logs
- Docker container status
- Docker statistics
- CPU and memory usage
- disk usage
- disk I/O
- network connectivity
- monitoring metrics
- configuration
- container dependencies

The goal is to identify the root cause instead of changing several
components at the same time.

---

## Infrastructure and Application

Application performance can be affected by infrastructure components
even when application code has not changed.

Docker, monitoring agents, databases, storage and the VM itself can all
influence application behaviour.

Infrastructure changes should therefore be tested and monitored after
updates.
