package com.mohammadreza.common;

/**
 * This is an Exception type for our business logic and application
 */
public class ApplicationException extends Exception {
    public ApplicationException(String message) {
        super(message);
    }
}
