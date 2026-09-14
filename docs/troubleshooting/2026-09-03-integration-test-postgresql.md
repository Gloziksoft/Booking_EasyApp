# Integration Test — PostgreSQL Database

## Context

During development of BookingEasyApp, an integration test was created
to verify application behaviour together with the database.

Unlike a unit test with Mockito, this test does not mock the complete
persistence layer.

The test starts the Spring Boot application context and works with the
PostgreSQL database configured for the local test profile.

---

## Problem / Investigation

The integration test initially used:

    @SpringBootTest

together with:

    @AutoConfigureTestDatabase(replace = NONE)

and:

    @ActiveProfiles("local")

The intention was to use the real PostgreSQL database instead of
automatically replacing it with an embedded database.

During testing, an H2 database configuration caused a problem because
the expected PostgreSQL schema `BOOKING_APP` was not available in H2.

The test therefore could not correctly reproduce the database
environment used by the application.

---

## Database Configuration

The local profile is configured to use PostgreSQL.

The integration test therefore uses the existing PostgreSQL database
configuration instead of replacing it with H2.

This makes the test closer to the real application environment.

---

## Test Behaviour

The integration test verifies the persistence behaviour of the
application.

The test checks the number of reservations before and after the
operation.

The general flow is:

1. read the number of existing reservations
2. execute the reservation operation
3. read the number of reservations again
4. verify that the expected database change occurred

The test therefore verifies more than the service method itself.

It also verifies that the application can communicate with the database
and persist the expected data.

---

## Unit Test vs Integration Test

The unit test for `ReservationServiceImpl` uses Mockito to isolate the
service from its dependencies.

The integration test uses the Spring Boot application context and a
real PostgreSQL database.

The difference is therefore:

    Unit test
        ↓
    Service logic
        ↓
    Mocked dependencies

    Integration test
        ↓
    Spring Boot application
        ↓
    Service / Repository
        ↓
    PostgreSQL

Both types of tests have a different purpose.

---

## Lesson Learned

A unit test and an integration test should not be treated as the same
thing.

Unit tests are useful for fast and isolated verification of business
logic.

Integration tests are useful when the interaction between application
components and infrastructure such as a database also needs to be
verified.

The database configuration used by the test must match the intended
test environment.

For future improvements, a dedicated test database or Testcontainers
can provide better isolation from the normal local database.

---

## Practical Experience

This investigation showed that database-related test failures are not
always caused by the service code itself.

The complete test environment must also be checked:

- Spring profile
- datasource configuration
- database type
- schema
- database availability
- test database isolation

This is an important part of troubleshooting integration tests.
