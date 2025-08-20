package com.example.tutorial.common.exceptions;

/**
 * Custom exception class for handling application-specific errors which are functional in nature.
 */
public class ApplicationFunctionalException extends RuntimeException {

    public ApplicationFunctionalException(String message) {
        super(message);
    }

    public ApplicationFunctionalException(String message, Throwable cause) {
        super(message, cause);
    }
}
