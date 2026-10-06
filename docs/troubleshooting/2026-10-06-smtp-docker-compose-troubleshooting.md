# 2026-10-06 – SMTP / Docker Compose troubleshooting

## Summary

Today I investigated an SMTP authentication problem in the Booking Spring Boot application.

The same application behaved differently locally and in production. The investigation showed that the value configured in `.env` was not necessarily the same value received by the application/container.

## Investigation

### Local development

The SMTP password was present in the local `.env`, but Spring Boot did not receive the expected value.

Diagnostic output showed:

```text
SPRING_MAIL_USERNAME present: true
SPRING_MAIL_PASSWORD length: 2
```

The SMTP server returned:

```text
Authentication failed
```

The actual password in the local `.env` was verified separately with Bash and had 15 characters.

### Fix in local environment

The local environment file was adjusted so that the password containing special characters was parsed correctly.

After restarting the application, the password-reset e-mail was successfully delivered.

This confirmed that the SMTP credentials themselves were valid and that the problem was related to how the environment variable value was parsed/passed.

## Production verification

Production was checked directly on the Oracle Cloud VM.

The `.env` value was inspected without exposing the password:

```text
SPRING_MAIL_PASSWORD length: 17
```

The value inside the running Docker container was then checked only by length:

```text
SPRING_MAIL_PASSWORD length: 16
```

The production password-reset flow was tested and the e-mail was successfully delivered.

## Important observation

The values observed at different stages were:

```text
LOCAL
.env              → 15 characters
Spring Boot       → 2 characters
SMTP              → Authentication failed

PRODUCTION
.env              → 17 characters
Docker container  → 16 characters
SMTP              → E-mail sent successfully
```

The exact values were never printed; only their lengths were checked.

## Main lesson

Do not assume that a value written in `.env` is identical to the value received by the application.

When debugging environment variables:

```text
.env
  ↓
Docker Compose / environment parsing
  ↓
Container environment
  ↓
Spring Boot
  ↓
External service
```

Verify the value at the relevant stage without exposing secrets.

A single special character can change the resulting value and therefore the application behaviour.

## Documentation / LinkedIn

The issue was also documented as a technical troubleshooting example:

**One `$` in `.env` — different result: Local vs Production**

The example focuses on the difference between the value stored in `.env` and the value actually received by the application/container.

## Status

- [x] Local SMTP problem identified
- [x] Local environment parsing issue fixed
- [x] Local password-reset e-mail verified
- [x] Production `.env` checked safely
- [x] Production container environment checked safely
- [x] Production password-reset e-mail verified
- [x] No passwords exposed in diagnostics
- [x] Troubleshooting documented

## Next

Continue with the Booking application security/input-validation work (Point 5 from the review), while preserving the existing project architecture.
