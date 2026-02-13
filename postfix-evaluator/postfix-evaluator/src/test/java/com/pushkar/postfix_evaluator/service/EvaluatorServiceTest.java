package com.pushkar.postfix_evaluator.service;

import com.pushkar.postfix_evaluator.exception.EvaluationException;
import com.pushkar.postfix_evaluator.exception.InvalidEquationException;
import com.pushkar.postfix_evaluator.model.TreeNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for EvaluatorService
 */
@DisplayName("Evaluator Service Tests")
class EvaluatorServiceTest {
    
    @Test
    @DisplayName("should evaluate simple arithmetic")
    void testSimpleArithmetic() {
        // Tree: 3 + 2
        TreeNode root = new TreeNode();
        root.setValue("+");
        root.setLeft(new TreeNode("3", null, null));
        root.setRight(new TreeNode("2", null, null));
        
        Map<String, Number> vars = new HashMap<>();
        double result = EvaluatorService.evaluate(root, vars);
        assertEquals(5.0, result);
    }
    
    @Test
    @DisplayName("should evaluate expression with variables")
    void testWithVariables() {
        // Tree: x + 2 (from "x + 2")
        TreeNode root = new TreeNode();
        root.setValue("+");
        root.setLeft(new TreeNode("x", null, null));
        root.setRight(new TreeNode("2", null, null));
        
        Map<String, Number> vars = new HashMap<>();
        vars.put("x", 5);
        
        double result = EvaluatorService.evaluate(root, vars);
        assertEquals(7.0, result);
    }
    
    @Test
    @DisplayName("should evaluate multiplication")
    void testMultiplication() {
        // Tree: 3 * x
        TreeNode root = new TreeNode();
        root.setValue("*");
        root.setLeft(new TreeNode("3", null, null));
        root.setRight(new TreeNode("x", null, null));
        
        Map<String, Number> vars = new HashMap<>();
        vars.put("x", 4);
        
        double result = EvaluatorService.evaluate(root, vars);
        assertEquals(12.0, result);
    }
    
    @Test
    @DisplayName("should evaluate division")
    void testDivision() {
        // Tree: 10 / 2
        TreeNode root = new TreeNode();
        root.setValue("/");
        root.setLeft(new TreeNode("10", null, null));
        root.setRight(new TreeNode("2", null, null));
        
        Map<String, Number> vars = new HashMap<>();
        double result = EvaluatorService.evaluate(root, vars);
        assertEquals(5.0, result);
    }
    
    @Test
    @DisplayName("should throw exception for division by zero")
    void testDivisionByZero() {
        // Tree: x / 0
        TreeNode root = new TreeNode();
        root.setValue("/");
        root.setLeft(new TreeNode("x", null, null));
        root.setRight(new TreeNode("0", null, null));
        
        Map<String, Number> vars = new HashMap<>();
        vars.put("x", 5);
        
        assertThrows(EvaluationException.class, () -> EvaluatorService.evaluate(root, vars));
    }
    
    @Test
    @DisplayName("should evaluate power operator")
    void testPowerOperator() {
        // Tree: 2 ^ 3
        TreeNode root = new TreeNode();
        root.setValue("^");
        root.setLeft(new TreeNode("2", null, null));
        root.setRight(new TreeNode("3", null, null));
        
        Map<String, Number> vars = new HashMap<>();
        double result = EvaluatorService.evaluate(root, vars);
        assertEquals(8.0, result);
    }
    
    @Test
    @DisplayName("should handle subtraction")
    void testSubtraction() {
        // Tree: 10 - 3
        TreeNode root = new TreeNode();
        root.setValue("-");
        root.setLeft(new TreeNode("10", null, null));
        root.setRight(new TreeNode("3", null, null));
        
        Map<String, Number> vars = new HashMap<>();
        double result = EvaluatorService.evaluate(root, vars);
        assertEquals(7.0, result);
    }
    
    @Test
    @DisplayName("should evaluate complex expression with multiple variables")
    void testComplexExpression() {
        // Tree: 3*x + 2*y
        TreeNode root = new TreeNode();
        root.setValue("+");
        
        TreeNode multLeft = new TreeNode();
        multLeft.setValue("*");
        multLeft.setLeft(new TreeNode("3", null, null));
        multLeft.setRight(new TreeNode("x", null, null));
        
        TreeNode multRight = new TreeNode();
        multRight.setValue("*");
        multRight.setLeft(new TreeNode("2", null, null));
        multRight.setRight(new TreeNode("y", null, null));
        
        root.setLeft(multLeft);
        root.setRight(multRight);
        
        Map<String, Number> vars = new HashMap<>();
        vars.put("x", 2);
        vars.put("y", 3);
        
        // Expected: 3*2 + 2*3 = 6 + 6 = 12
        double result = EvaluatorService.evaluate(root, vars);
        assertEquals(12.0, result);
    }
    
    @Test
    @DisplayName("should throw exception for missing variable")
    void testMissingVariable() {
        // Tree: x + y
        TreeNode root = new TreeNode();
        root.setValue("+");
        root.setLeft(new TreeNode("x", null, null));
        root.setRight(new TreeNode("y", null, null));
        
        Map<String, Number> vars = new HashMap<>();
        vars.put("x", 5);
        // Missing y
        
        assertThrows(EvaluationException.class, () -> EvaluatorService.evaluate(root, vars));
    }
    
    @Test
    @DisplayName("should throw exception for null tree")
    void testNullTree() {
        Map<String, Number> vars = new HashMap<>();
        assertThrows(EvaluationException.class, () -> EvaluatorService.evaluate(null, vars));
    }
    
    @Test
    @DisplayName("should throw exception for null variables map")
    void testNullVariablesMap() {
        TreeNode root = new TreeNode("x", null, null);
        assertThrows(InvalidEquationException.class, () -> EvaluatorService.evaluate(root, null));
    }
    
    @Test
    @DisplayName("should extract variables from tree")
    void testExtractVariables() {
        // Tree: x + y
        TreeNode root = new TreeNode();
        root.setValue("+");
        root.setLeft(new TreeNode("x", null, null));
        root.setRight(new TreeNode("y", null, null));
        
        Set<String> variables = EvaluatorService.extractVariables(root);
        assertEquals(2, variables.size());
        assertTrue(variables.contains("x"));
        assertTrue(variables.contains("y"));
    }
    
    @Test
    @DisplayName("should extract variables excluding numbers")
    void testExtractVariablesExcludingNumbers() {
        // Tree: 2*x + 3
        TreeNode root = new TreeNode();
        root.setValue("+");
        
        TreeNode mult = new TreeNode();
        mult.setValue("*");
        mult.setLeft(new TreeNode("2", null, null));
        mult.setRight(new TreeNode("x", null, null));
        
        root.setLeft(mult);
        root.setRight(new TreeNode("3", null, null));
        
        Set<String> variables = EvaluatorService.extractVariables(root);
        assertEquals(1, variables.size());
        assertTrue(variables.contains("x"));
    }
}
