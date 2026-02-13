package com.pushkar.postfix_evaluator.service;

import com.pushkar.postfix_evaluator.dto.EquationDTO;
import com.pushkar.postfix_evaluator.exception.EquationNotFoundException;
import com.pushkar.postfix_evaluator.exception.InvalidEquationException;
import com.pushkar.postfix_evaluator.model.Equation;
import com.pushkar.postfix_evaluator.model.TreeNode;
import com.pushkar.postfix_evaluator.parser.EquationParser;
import com.pushkar.postfix_evaluator.parser.InfixNotationReconstructor;
import com.pushkar.postfix_evaluator.repository.EquationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for managing equations
 */
@Service
public class EquationService {
    
    @Autowired
    private EquationRepository equationRepository;
    
    /**
     * Stores an equation by parsing it and building its tree structure
     * 
     * @param equationString the equation in infix notation
     * @return the generated equation ID
     * @throws InvalidEquationException if the equation is invalid
     */
    public String storeEquation(String equationString) {
        if (equationString == null || equationString.trim().isEmpty()) {
            throw new InvalidEquationException("Equation cannot be null or empty");
        }
        
        // Parse equation and build tree
        TreeNode rootNode = EquationParser.parse(equationString.trim());
        
        // Create and save equation
        Equation equation = new Equation();
        equation.setEquationInfix(equationString.trim());
        equation.setRootNode(rootNode);
        
        return equationRepository.save(equation);
    }
    
    /**
     * Retrieves all stored equations in infix notation
     * 
     * @return list of equation DTOs
     */
    public List<EquationDTO> getAllEquations() {
        Collection<Equation> equations = equationRepository.findAll();
        return equations.stream()
                .map(eq -> new EquationDTO(eq.getId(), eq.getEquationInfix()))
                .collect(Collectors.toList());
    }
    
    /**
     * Retrieves a specific equation by ID
     * 
     * @param equationId the equation ID
     * @return the equation
     * @throws EquationNotFoundException if not found
     */
    public Equation getEquationById(String equationId) {
        Equation equation = equationRepository.findById(equationId);
        if (equation == null) {
            throw new EquationNotFoundException(
                "Equation with ID '" + equationId + "' not found"
            );
        }
        return equation;
    }
    
    /**
     * Retrieves a specific equation as DTO
     * 
     * @param equationId the equation ID
     * @return the equation DTO
     * @throws EquationNotFoundException if not found
     */
    public EquationDTO getEquationDTOById(String equationId) {
        Equation equation = getEquationById(equationId);
        return new EquationDTO(equation.getId(), equation.getEquationInfix());
    }
    
    /**
     * Reconstructs an equation in infix notation from its tree
     * 
     * @param equation the equation
     * @return the infix notation string
     */
    public String reconstructEquation(Equation equation) {
        return InfixNotationReconstructor.reconstruct(equation.getRootNode());
    }
}
