package com.pushkar.postfix_evaluator.parser;

import com.pushkar.postfix_evaluator.exception.InvalidEquationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Tokenizer
 */
@DisplayName("Tokenizer Tests")
class TokenizerTest {
    
    @Test
    @DisplayName("should tokenize simple arithmetic expression")
    void testSimpleExpression() {
        List<String> tokens = Tokenizer.tokenize("3 + 2 * x");
        assertEquals(5, tokens.size());
        assertEquals("3", tokens.get(0));
        assertEquals("+", tokens.get(1));
        assertEquals("2", tokens.get(2));
        assertEquals("*", tokens.get(3));
        assertEquals("x", tokens.get(4));
    }
    
    @Test
    @DisplayName("should tokenize complex expression with parentheses")
    void testComplexExpression() {
        List<String> tokens = Tokenizer.tokenize("(x + 2) * (y - 1)");
        assertEquals(11, tokens.size());
    }
    
    @Test
    @DisplayName("should tokenize decimal numbers")
    void testDecimalNumbers() {
        List<String> tokens = Tokenizer.tokenize("3.14 + 2.71");
        assertEquals(3, tokens.size());
        assertEquals("3.14", tokens.get(0));
        assertEquals("2.71", tokens.get(2));
    }
    
    @Test
    @DisplayName("should tokenize power operator")
    void testPowerOperator() {
        List<String> tokens = Tokenizer.tokenize("x^2 + y^3");
        assertEquals(5, tokens.size());
        assertEquals("^", tokens.get(1));
        assertEquals("^", tokens.get(3));
    }
    
    @Test
    @DisplayName("should handle whitespace correctly")
    void testWhitespaceHandling() {
        List<String> tokens1 = Tokenizer.tokenize("x + 2");
        List<String> tokens2 = Tokenizer.tokenize("x+2");
        assertEquals(tokens1, tokens2);
    }
    
    @Test
    @DisplayName("should throw exception for null equation")
    void testNullEquation() {
        assertThrows(IllegalArgumentException.class, () -> Tokenizer.tokenize(null));
    }
    
    @Test
    @DisplayName("should throw exception for empty equation")
    void testEmptyEquation() {
        assertThrows(IllegalArgumentException.class, () -> Tokenizer.tokenize(""));
    }
    
    @Test
    @DisplayName("should throw exception for invalid characters")
    void testInvalidCharacters() {
        assertThrows(IllegalArgumentException.class, () -> Tokenizer.tokenize("x & y"));
        assertThrows(IllegalArgumentException.class, () -> Tokenizer.tokenize("x @ 2"));
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"42", "3.14", "0", "1.5"})
    @DisplayName("should identify numbers correctly")
    void testIsNumber(String token) {
        assertTrue(Tokenizer.isNumber(token));
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"x", "var", "x1", "_var"})
    @DisplayName("should identify variables correctly")
    void testIsVariable(String token) {
        assertTrue(Tokenizer.isVariable(token));
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"+", "-", "*", "/", "^"})
    @DisplayName("should identify operators correctly")
    void testIsOperator(String token) {
        assertTrue(Tokenizer.isOperator(token));
    }
}
