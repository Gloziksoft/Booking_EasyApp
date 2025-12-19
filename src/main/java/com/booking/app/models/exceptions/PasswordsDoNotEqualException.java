package com.booking.app.models.exceptions;

/**
 * Exception thrown when the provided passwords do not match during registration or password change.
 */
public class PasswordsDoNotEqualException extends RuntimeException {
    public PasswordsDoNotEqualException(String message) {
        super(message);
    }
}
