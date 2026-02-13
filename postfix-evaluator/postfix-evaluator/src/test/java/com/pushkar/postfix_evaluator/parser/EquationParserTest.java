package com.pushkar.postfix_evaluator.parser;

import com.pushkar.postfix_evaluator.exception.InvalidEquationException;
import com.pushkar.postfix_evaluator.model.TreeNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for EquationParser (end-to-end parsing)
 */
@DisplayName("Equation Parser Tests")
class EquationParserTest {
    
    @Test
    @DisplayName("should parse simple arithmetic equation")
    void testSimpleExpression() {
        TreeNode root = EquationParser.parse("3 + 2");
        assertNotNull(root);
        assertEquals("+", root.getValue());
    }
    
    @Test
    @DisplayName("should parse equation with variables")
    void testEquationWithVariables() {
        TreeNode root = EquationParser.parse("3*x + 2*y - z");
        assertNotNull(root);
        assertEquals("-", root.getValue());
    }
    
    @Test
    @DisplayName("should parse equation with parentheses")
    void testEquationWithParentheses() {
        TreeNode root = EquationParser.parse("(x + 2) * (y - 1)");
        assertNotNull(root);
        assertTrue(root.isOperator());
    }
    
    @Test
    @DisplayName("should parse equation with power operator")
    void testEquationWithPower() {
        TreeNode root = EquationParser.parse("x^2 + y^3");
        assertNotNull(root);
        assertEquals("+", root.getValue());
    }
    
    @Test
    @DisplayName("should throw exception for empty equation")
    void testEmptyEquation() {
        assertThrows(InvalidEquationException.class, () -> EquationParser.parse(""));
    }
    
    @Test
    @DisplayName("should throw exception for null equation")
    void testNullEquation() {
        assertThrows(InvalidEquationException.class, () -> EquationParser.parse(null));
    }
    
    @Test
    @DisplayName("should throw exception for mismatched parentheses")
    void testMismatchedParentheses() {
        assertThrows(InvalidEquationException.class, () -> EquationParser.parse("(x + 2"));
        assertThrows(InvalidEquationException.class, () -> EquationParser.parse("x + 2)"));
    }
    
    @Test
    @DisplayName("should throw exception for invalid tokens")
    void testInvalidTokens() {
        assertThrows(InvalidEquationException.class, () -> EquationParser.parse("x & y"));
    }
    
    @Test
    @DisplayName("should throw exception for missing operand")
    void testMissingOperand() {
        assertThrows(InvalidEquationException.class, () -> EquationParser.parse("x +"));
        assertThrows(InvalidEquationException.class, () -> EquationParser.parse("+ x"));
    }
    
    @Test
    @DisplayName("should throw exception for consecutive operators")
    void testConsecutiveOperators() {
        assertThrows(InvalidEquationException.class, () -> EquationParser.parse("x ++ y"));
    }
    
    @Test
    @DisplayName("should handle complex equation with mixed operators")
    void testComplexMixedEquation() {
        TreeNode root = EquationParser.parse("2*x + 3*y - 5*z + a/b");
        assertNotNull(root);
        assertTrue(root.isOperator());
    }
    
    @Test
    @DisplayName("should parse equation with decimal numbers")
    void testDecimalNumbers() {
        TreeNode root = EquationParser.parse("3.14 * x + 2.71");
        assertNotNull(root);
        assertEquals("+", root.getValue());
    }
}
