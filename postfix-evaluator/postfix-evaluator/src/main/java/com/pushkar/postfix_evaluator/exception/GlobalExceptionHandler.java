package com.pushkar.postfix_evaluator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.pushkar.postfix_evaluator.dto.ErrorResponseDTO;

/**
 * Global exception handler for REST API
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(InvalidEquationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponseDTO> handleInvalidEquationException(
            InvalidEquationException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
            "Invalid Equation",
            ex.getMessage(),
            System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(EquationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponseDTO> handleEquationNotFoundException(
            EquationNotFoundException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
            "Not Found",
            ex.getMessage(),
            System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(EvaluationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponseDTO> handleEvaluationException(
            EvaluationException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
            "Evaluation Error",
            ex.getMessage(),
            System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponseDTO> handleGeneralException(Exception ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
            "Internal Server Error",
            ex.getMessage(),
            System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
