package com.pushkar.postfix_evaluator.controller;

import com.pushkar.postfix_evaluator.dto.EvaluationRequestDTO;
import com.pushkar.postfix_evaluator.dto.EvaluationResponseDTO;
import com.pushkar.postfix_evaluator.dto.EquationDTO;
import com.pushkar.postfix_evaluator.dto.EquationRequestDTO;
import com.pushkar.postfix_evaluator.dto.EquationResponseDTO;
import com.pushkar.postfix_evaluator.dto.EquationsListResponseDTO;
import com.pushkar.postfix_evaluator.exception.InvalidEquationException;
import com.pushkar.postfix_evaluator.model.Equation;
import com.pushkar.postfix_evaluator.service.EquationService;
import com.pushkar.postfix_evaluator.service.EvaluatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST Controller for equation operations
 */
@RestController
@RequestMapping("/api/equations")
public class EquationController {
    
    @Autowired
    private EquationService equationService;
    
    /**
     * Stores an equation
     * POST /api/equations/store
     * 
     * @param request the equation request containing the equation string
     * @return response with equation ID
     */
    @PostMapping("/store")
    public ResponseEntity<EquationResponseDTO> storeEquation(
            @RequestBody EquationRequestDTO request) {
        
        if (request == null || request.getEquation() == null) {
            throw new InvalidEquationException("Request body and equation cannot be null");
        }
        
        String equationId = equationService.storeEquation(request.getEquation());
        
        EquationResponseDTO response = new EquationResponseDTO(
            "Equation stored successfully",
            equationId
        );
        
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    /**
     * Retrieves all stored equations
     * GET /api/equations
     * 
     * @return list of all stored equations
     */
    @GetMapping
    public ResponseEntity<EquationsListResponseDTO> getAllEquations() {
        List<EquationDTO> equations = equationService.getAllEquations();
        EquationsListResponseDTO response = new EquationsListResponseDTO(equations);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    /**
     * Evaluates an equation with given variable values
     * POST /api/equations/{equationId}/evaluate
     * 
     * @param equationId the ID of the equation to evaluate
     * @param request the evaluation request containing variable values
     * @return response with evaluation result
     */
    @PostMapping("/{equationId}/evaluate")
    public ResponseEntity<EvaluationResponseDTO> evaluateEquation(
            @PathVariable String equationId,
            @RequestBody EvaluationRequestDTO request) {
        
        if (request == null || request.getVariables() == null) {
            throw new InvalidEquationException("Request body and variables map cannot be null");
        }
        
        // Retrieve the equation
        Equation equation = equationService.getEquationById(equationId);
        
        // Evaluate the expression tree
        Double result = EvaluatorService.evaluate(
            equation.getRootNode(),
            request.getVariables()
        );
        
        // Create response
        EvaluationResponseDTO response = new EvaluationResponseDTO(
            equation.getId(),
            equation.getEquationInfix(),
            request.getVariables(),
            result
        );
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
