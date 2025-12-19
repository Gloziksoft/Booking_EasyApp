package com.booking.app.models.exceptions;

/**
 * Exception thrown when attempting to register or create a user
 * with an email that already exists in the system.
 */
public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String message) {
        super(message);
    }
}
