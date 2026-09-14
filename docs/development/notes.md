# Development Notes

Technical development notes for the BookingEasyApp application.

This document contains general development knowledge, implementation
approaches, code structure and testing principles.

---

## Application Development

The application is developed using Java and Spring Boot.

Development focuses on:

- REST API development
- validation and exception handling
- database-related changes
- DTO and Entity design
- security implementation
- code refactoring
- testing
- technical decisions and lessons learned
- development experiments

---

## Code Structure

The application is organized into clearly separated layers.

### Controller

Controllers handle HTTP requests and expose the REST API.

### Service

Services contain the business logic of the application.

### Repository

Repositories handle communication with the database and persistence layer.

### DTO

DTOs are used for communication between the API and application layers.

The separation of responsibilities keeps the code structure simple and
makes the purpose of individual classes easier to understand.

---

## Testing

Testing focuses on application behaviour and business logic.

### Unit Tests

JUnit is used for testing service-layer business logic.

Mockito is used to isolate the tested component by mocking its dependencies.

Typical unit tests cover:

- successful scenarios
- expected error cases
- validation of business rules
- interaction with mocked dependencies

Unit tests provide fast feedback during development.

### Integration Tests

Integration tests verify the interaction between multiple application layers.

They can also verify communication with the database and persistence layer.

The goal is to verify that the main application flow works correctly as a whole.

Integration tests provide confidence that the application works correctly
with its real dependencies.

---

## Testing Approach

Tests should focus on application behaviour rather than implementation details.

A practical testing approach is:

1. Test business logic with unit tests.
2. Mock external dependencies when isolation is required.
3. Test expected error scenarios.
4. Use integration tests when interaction between multiple layers or the
   database needs to be verified.
5. Keep tests readable and focused on one behaviour.

---

## Development Principles

The application development follows simple and consistent design principles.

The main goals are:

- clear separation of responsibilities
- simple and readable code
- avoiding unnecessary complexity
- keeping classes focused on their purpose
- reducing duplicated logic
- refactoring when the structure becomes unnecessarily complicated

---

## Technical Decisions and Experiments

Development experiments and technical decisions should be documented
when they provide useful lessons for future development.

Specific problems encountered during development are documented separately
in:

    docs/troubleshooting/

This keeps the general development notes separate from concrete incidents
and debugging cases.
