package com.pushkar.postfix_evaluator.parser;

import com.pushkar.postfix_evaluator.exception.InvalidEquationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for InfixToPostfixConverter
 */
@DisplayName("Infix to Postfix Converter Tests")
class InfixToPostfixConverterTest {
    
    @Test
    @DisplayName("should convert simple addition")
    void testSimpleAddition() {
        List<String> infix = List.of("3", "+", "2");
        List<String> postfix = InfixToPostfixConverter.convert(infix);
        assertEquals(List.of("3", "2", "+"), postfix);
    }
    
    @Test
    @DisplayName("should convert expression with operator precedence")
    void testOperatorPrecedence() {
        List<String> infix = List.of("3", "+", "2", "*", "x");
        List<String> postfix = InfixToPostfixConverter.convert(infix);
        assertEquals(List.of("3", "2", "x", "*", "+"), postfix);
    }
    
    @Test
    @DisplayName("should handle parentheses")
    void testParentheses() {
        List<String> infix = List.of("(", "3", "+", "2", ")", "*", "x");
        List<String> postfix = InfixToPostfixConverter.convert(infix);
        assertEquals(List.of("3", "2", "+", "x", "*"), postfix);
    }
    
    @Test
    @DisplayName("should handle power operator and right associativity")
    void testPowerOperator() {
        List<String> infix = List.of("2", "^", "3", "^", "2");
        List<String> postfix = InfixToPostfixConverter.convert(infix);
        // Right associative: 2^(3^2)
        assertEquals(List.of("2", "3", "2", "^", "^"), postfix);
    }
    
    @Test
    @DisplayName("should handle subtraction (left associative)")
    void testSubtractionLeftAssociative() {
        List<String> infix = List.of("10", "-", "5", "-", "2");
        List<String> postfix = InfixToPostfixConverter.convert(infix);
        // Left associative: (10-5)-2 = 3
        assertEquals(List.of("10", "5", "-", "2", "-"), postfix);
    }
    
    @Test
    @DisplayName("should handle division (left associative)")
    void testDivisionLeftAssociative() {
        List<String> infix = List.of("20", "/", "4", "/", "2");
        List<String> postfix = InfixToPostfixConverter.convert(infix);
        // Left associative: (20/4)/2 = 2.5
        assertEquals(List.of("20", "4", "/", "2", "/"), postfix);
    }
    
    @Test
    @DisplayName("should convert complex expression")
    void testComplexExpression() {
        List<String> infix = List.of("3", "*", "x", "+", "2", "*", "y", "-", "z");
        List<String> postfix = InfixToPostfixConverter.convert(infix);
        assertEquals(List.of("3", "x", "*", "2", "y", "*", "+", "z", "-"), postfix);
    }
    
    @Test
    @DisplayName("should handle nested parentheses")
    void testNestedParentheses() {
        List<String> infix = List.of("(", "(", "a", "+", "b", ")", "*", "c", ")");
        List<String> postfix = InfixToPostfixConverter.convert(infix);
        assertEquals(List.of("a", "b", "+", "c", "*"), postfix);
    }
    
    @Test
    @DisplayName("should throw exception for mismatched parentheses")
    void testMismatchedParentheses() {
        List<String> infix = List.of("(", "3", "+", "2");
        assertThrows(IllegalArgumentException.class, () -> InfixToPostfixConverter.convert(infix));
    }
    
    @Test
    @DisplayName("should throw exception for extra closing parenthesis")
    void testExtraClosingParenthesis() {
        List<String> infix = List.of("3", "+", "2", ")");
        assertThrows(IllegalArgumentException.class, () -> InfixToPostfixConverter.convert(infix));
    }
    
    @Test
    @DisplayName("should throw exception for insufficient operands")
    void testInsufficientOperands() {
        List<String> infix = List.of("+", "2");
        assertThrows(IllegalArgumentException.class, () -> InfixToPostfixConverter.convert(infix));
    }
    
    @Test
    @DisplayName("should throw exception for empty token list")
    void testEmptyTokenList() {
        assertThrows(IllegalArgumentException.class, () -> InfixToPostfixConverter.convert(List.of()));
    }
}
