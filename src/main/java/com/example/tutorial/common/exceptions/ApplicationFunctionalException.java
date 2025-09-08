package com.example.tutorial.common.exceptions;

import java.util.List;

/**
 * Custom exception class for handling application-specific errors which are functional in nature.
 */
public class ApplicationFunctionalException extends RuntimeException {
    private List<String> errors;

    public ApplicationFunctionalException(String message) {
        super(message);
    }

    public ApplicationFunctionalException(String message, List<String> errors) {
        super(message);
        this.errors= errors;
    }

    public ApplicationFunctionalException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationFunctionalException(String message, List<String> errors, Throwable cause) {
        super(message, cause);
        this.errors= errors;
    }

    // Getters and Setters
    public List<String> getErrors() {
        return errors;
    }
}
