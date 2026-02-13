package com.pushkar.postfix_evaluator.exception;

/**
 * Exception thrown when a requested equation is not found
 */
public class EquationNotFoundException extends RuntimeException {
    public EquationNotFoundException(String message) {
        super(message);
    }
}
