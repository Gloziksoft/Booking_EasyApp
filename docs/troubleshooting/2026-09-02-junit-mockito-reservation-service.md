# JUnit / Mockito — ReservationServiceImpl Testing

**Date:** 2026-09-02

## Context

During development of BookingEasyApp, the reservation service was tested
with JUnit and Mockito.

The goal was to test the business logic of `ReservationServiceImpl`
without using the real database or real external services.

---

## Problem / Investigation

The service depends on several other components.

The unit test therefore needed to isolate `ReservationServiceImpl`
from its dependencies.

The test uses Mockito to mock dependencies such as:

- `ReservationRepository`
- `UserRepository`
- `OfferRepository`
- `ReservationMapper`
- `OfferMapper`
- `EmailService`

`ReservationServiceImpl` is injected into the test with the mocked
dependencies.

This makes it possible to test the service-layer logic independently.

---

## Test Structure

The test class uses:

- JUnit 5
- Mockito
- `@Mock`
- `@InjectMocks`

The tested class is:

    ReservationServiceImpl

The main scenarios covered include:

- successful reservation creation
- invalid reservation dates
- finding an existing reservation by ID

---

## Successful Reservation Creation

The successful creation test verifies the main service flow.

The service:

1. creates a reservation entity
2. updates the reservation fields
3. associates the user
4. associates the offer
5. saves the reservation through the repository
6. sends confirmation emails
7. maps the saved entity back to the expected result

The test mocks the repository and other dependencies so that the test
does not require a real database.

---

## Error Scenario

An invalid reservation date scenario is tested separately.

The purpose is to verify that invalid input is rejected by the service
and that the expected exception is thrown.

This is important because testing only successful scenarios would not
verify the application's business rules.

---

## Finding a Reservation

Another test verifies that an existing reservation can be retrieved
by its ID.

The repository is mocked to return the expected entity and the service
result is verified.

This keeps the test focused on the service behaviour rather than the
database implementation.

---

## Email Sending and `@Async`

The reservation service also triggers confirmation emails.

The email operation is asynchronous in the application.

During unit testing, the email service itself is mocked.

This keeps the unit test independent from the real email infrastructure.

The test therefore verifies the service behaviour without actually
sending an email.

---

## Lesson Learned

JUnit and Mockito make it possible to test service-layer business logic
without starting the complete application or connecting to the real
database and external services.

The important principle is to isolate the component being tested and
mock its dependencies.

This results in tests that are:

- fast
- focused
- repeatable
- independent of external infrastructure

More complex problems involving integration tests and the database are
documented separately in the troubleshooting documentation.
