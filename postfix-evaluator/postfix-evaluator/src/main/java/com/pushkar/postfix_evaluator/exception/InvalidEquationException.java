package com.pushkar.postfix_evaluator.exception;

/**
 * Exception thrown when an equation is invalid or malformed
 */
public class InvalidEquationException extends RuntimeException {
    public InvalidEquationException(String message) {
        super(message);
    }
    
    public InvalidEquationException(String message, Throwable cause) {
        super(message, cause);
    }
}
