# Docker / cAdvisor — Monitoring and VM Latency Investigation

**Date:** 2026-07-15

## Context

BookingEasyApp was running on a resource-constrained production VM
with Docker and a monitoring stack based on cAdvisor and Grafana.

The application itself had not changed when the performance problem
appeared.

---

## Trigger

The issue appeared after updating Docker and cAdvisor.

Before the updates, the monitoring stack was running without the
observed performance impact.

---

## Problem

After the Docker and cAdvisor updates, increased resource usage and
latency were observed on the BookingEasyApp production VM.

The investigation therefore focused not only on the application but
also on the infrastructure and monitoring stack.

---

## Investigation

The resource usage was investigated incrementally.

The following areas were checked:

- CPU
- memory
- network
- disk
- disk I/O

Docker statistics and Grafana were used to observe the resource usage.

The behaviour was also compared with the InsuranceApp environment.

The goal was to identify which part of the monitoring stack was
causing the additional overhead.

---

## Finding / Root Cause

The changed behaviour appeared after the Docker and cAdvisor updates.

Disk-related cAdvisor metrics had the most noticeable performance
impact on the BookingEasyApp VM.

This showed that the increased latency was not necessarily caused by
the Java application itself.

The monitoring configuration was also capable of affecting the
performance of a resource-constrained VM.

---

## Action

Different cAdvisor metric configurations were tested.

The purpose was to isolate the source of the increased overhead and
find a suitable monitoring configuration.

The investigation therefore followed an incremental approach instead
of disabling the complete monitoring stack immediately.

---

## Lesson Learned

Infrastructure changes can affect application performance even when
the application code has not changed.

Docker, cAdvisor and other monitoring components consume system
resources and their impact can be more visible on a resource-constrained
VM.

After infrastructure or monitoring updates, resource usage and
application latency should therefore be verified.

---

## Practical Troubleshooting Approach

The investigation followed this general process:

    Application latency
          ↓
    Resource usage
          ↓
    Docker statistics
          ↓
    Grafana metrics
          ↓
    CPU / memory / network / disk / disk I/O
          ↓
    cAdvisor configuration
          ↓
    Compare environments
          ↓
    Isolate the source of overhead

This approach helps separate application problems from infrastructure
or monitoring problems.
