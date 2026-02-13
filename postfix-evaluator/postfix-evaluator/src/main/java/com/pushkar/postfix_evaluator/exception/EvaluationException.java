package com.pushkar.postfix_evaluator.exception;

/**
 * Exception thrown during equation evaluation
 */
public class EvaluationException extends RuntimeException {
    public EvaluationException(String message) {
        super(message);
    }
    
    public EvaluationException(String message, Throwable cause) {
        super(message, cause);
    }
}
