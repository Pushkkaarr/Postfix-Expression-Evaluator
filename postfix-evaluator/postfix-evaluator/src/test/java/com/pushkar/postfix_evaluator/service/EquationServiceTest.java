package com.pushkar.postfix_evaluator.service;

import com.pushkar.postfix_evaluator.dto.EquationDTO;
import com.pushkar.postfix_evaluator.exception.EquationNotFoundException;
import com.pushkar.postfix_evaluator.exception.InvalidEquationException;
import com.pushkar.postfix_evaluator.model.Equation;
import com.pushkar.postfix_evaluator.repository.EquationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for EquationService
 */
@SpringBootTest
@DisplayName("Equation Service Tests")
class EquationServiceTest {
    
    @Autowired
    private EquationService equationService;
    
    @Autowired
    private EquationRepository equationRepository;
    
    @BeforeEach
    void setUp() {
        equationRepository.clear();
    }
    
    @Test
    @DisplayName("should store valid equation")
    void testStoreValidEquation() {
        String id = equationService.storeEquation("3*x + 2*y");
        assertNotNull(id);
        assertTrue(equationRepository.existsById(id));
    }
    
    @Test
    @DisplayName("should throw exception for null equation")
    void testStoreNullEquation() {
        assertThrows(InvalidEquationException.class, () -> equationService.storeEquation(null));
    }
    
    @Test
    @DisplayName("should throw exception for empty equation")
    void testStoreEmptyEquation() {
        assertThrows(InvalidEquationException.class, () -> equationService.storeEquation(""));
    }
    
    @Test
    @DisplayName("should throw exception for invalid equation")
    void testStoreInvalidEquation() {
        assertThrows(InvalidEquationException.class, () -> equationService.storeEquation("x ++ y"));
    }
    
    @Test
    @DisplayName("should retrieve all equations")
    void testGetAllEquations() {
        equationService.storeEquation("x + 1");
        equationService.storeEquation("y * 2");
        
        List<EquationDTO> equations = equationService.getAllEquations();
        assertEquals(2, equations.size());
    }
    
    @Test
    @DisplayName("should return empty list when no equations stored")
    void testGetAllEquationsEmpty() {
        List<EquationDTO> equations = equationService.getAllEquations();
        assertEquals(0, equations.size());
    }
    
    @Test
    @DisplayName("should get equation by ID")
    void testGetEquationById() {
        String id = equationService.storeEquation("3*x + 2");
        Equation equation = equationService.getEquationById(id);
        
        assertNotNull(equation);
        assertEquals("3*x + 2", equation.getEquationInfix());
    }
    
    @Test
    @DisplayName("should throw exception for non-existent equation ID")
    void testGetEquationByIdNotFound() {
        assertThrows(EquationNotFoundException.class, 
            () -> equationService.getEquationById("nonexistent"));
    }
    
    @Test
    @DisplayName("should reconstruct equation from tree")
    void testReconstructEquation() {
        String originalEquation = "x + 2 * y";
        String id = equationService.storeEquation(originalEquation);
        Equation equation = equationService.getEquationById(id);
        
        String reconstructed = equationService.reconstructEquation(equation);
        assertNotNull(reconstructed);
        assertFalse(reconstructed.isEmpty());
    }
    
    @Test
    @DisplayName("should handle equations with complex operations")
    void testComplexEquations() {
        String[] equations = {
            "a + b - c",
            "x * y / z",
            "p ^ q + r",
            "(a + b) * (c - d)"
        };
        
        for (String eq : equations) {
            String id = equationService.storeEquation(eq);
            assertNotNull(id);
            Equation retrieved = equationService.getEquationById(id);
            assertNotNull(retrieved);
        }
    }
}
