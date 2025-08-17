package com.example.tutorial.common.exceptions;

/**
 * Custom exception class for handling application-specific errors which are technical in nature
 * like runtime exceptions which should only be retried later.
 */
public class ApplicationTechnicalException extends RuntimeException {

    public ApplicationTechnicalException(String message) {
        super(message);
    }

    public ApplicationTechnicalException(String message, Throwable cause) {
        super(message, cause);
    }
}
