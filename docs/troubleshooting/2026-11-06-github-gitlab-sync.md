# GitHub → GitLab secondary repository

**Date:** 2026-11-06

## Configuration

- Primary: GitHub (`origin`)
- Secondary: GitLab (`gitlab`)
- Repository: `Gloziksoft/Booking_EasyApp`
- Branch: `main`

## Work performed

- Added GitLab as a second Git remote.
- Detected unrelated GitHub and GitLab histories.
- GitLab initially contained a different `main` commit.
- GitLab protected branch rejected the force push.
- Temporarily enabled **Allowed to force push** in GitLab branch rules.
- Updated GitLab `main` to match GitHub using `--force-with-lease`.
- Deleted the temporary archive branch.
- Confirmed that `investigate-monitoring` was already contained in `main`, then deleted it.
- Verified that only `main` remained on both remotes.

## Final verification

Both remotes pointed to:

`c0f09adfb5da1813f255b3decda4412cde625261`

No Git tags were present.

## Routine synchronization

```bash
git push origin main
git push gitlab main
```

## Important

GitHub remains the primary repository. GitLab is a secondary copy of Git content. Issues, pull requests, comments, releases and attachments are not synchronized.

Keep GitLab branch protection enabled. Force push should only be enabled temporarily when necessary.
