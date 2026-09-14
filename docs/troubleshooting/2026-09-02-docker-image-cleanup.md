# Docker Image Cleanup

**Date:** 2026-09-02

## Context

BookingEasyApp was running on a production VM with limited disk
resources.

During repeated application deployments, Docker images from previous
deployments remained on the VM.

Unused images could therefore gradually consume additional disk space.

---

## Problem

Before the cleanup was introduced, old unused Docker images accumulated
after application deployments.

The production VM therefore used more disk space than necessary.

The goal was to clean unused images automatically after a successful
deployment without affecting running containers.

---

## Investigation

The Docker image usage was checked before and after cleanup.

Before cleanup:

    Docker images: 24
    Image storage: 6.455 GB
    Reclaimable: 3.042 GB
    VM disk usage: approximately 41%

The cleanup command used was:

    docker image prune -f

---

## Action

Automatic Docker image cleanup was added to the production deployment
workflow.

After a successful application deployment, the VM executes:

    docker image prune -f

The command removes unused dangling Docker images created by previous
deployments.

---

## Result

After the cleanup:

    Docker images: 6
    Image storage: 2.988 GB
    Reclaimable: 0 B
    VM disk usage: approximately 33%

The cleanup removed old unused images while keeping the active
application, database and monitoring containers running.

The same automatic cleanup approach was also applied to the
InsuranceApp production deployment.

---

## Lesson Learned

Repeated Docker deployments can leave unused image layers on a
production VM.

On a VM with limited disk capacity, automatic cleanup can prevent
unnecessary disk consumption.

The cleanup should be performed only after a successful deployment so
that the currently used application image remains available.

---

## Practical Experience

This investigation connected application deployment with the underlying
Docker storage.

The important checks were:

- number of Docker images
- total image storage
- reclaimable storage
- VM disk usage
- running containers after cleanup

The result showed that a relatively simple Docker maintenance command
can significantly reduce disk usage on a resource-constrained VM.
