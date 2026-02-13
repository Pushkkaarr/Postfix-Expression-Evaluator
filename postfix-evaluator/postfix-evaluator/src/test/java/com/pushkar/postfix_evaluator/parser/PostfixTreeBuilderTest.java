package com.pushkar.postfix_evaluator.parser;

import com.pushkar.postfix_evaluator.exception.InvalidEquationException;
import com.pushkar.postfix_evaluator.model.TreeNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for PostfixTreeBuilder
 */
@DisplayName("Postfix Tree Builder Tests")
class PostfixTreeBuilderTest {
    
    @Test
    @DisplayName("should build tree for simple addition")
    void testSimpleAddition() {
        List<String> postfix = List.of("3", "2", "+");
        TreeNode root = PostfixTreeBuilder.buildTree(postfix);
        
        assertNotNull(root);
        assertEquals("+", root.getValue());
        assertEquals("3", root.getLeft().getValue());
        assertEquals("2", root.getRight().getValue());
    }
    
    @Test
    @DisplayName("should build tree for complex expression")
    void testComplexExpression() {
        List<String> postfix = List.of("3", "2", "+", "x", "*");
        TreeNode root = PostfixTreeBuilder.buildTree(postfix);
        
        assertEquals("*", root.getValue());
        assertEquals("+", root.getLeft().getValue());
        assertEquals("x", root.getRight().getValue());
    }
    
    @Test
    @DisplayName("should create leaf nodes for operands")
    void testLeafNodes() {
        List<String> postfix = List.of("5", "y", "-");
        TreeNode root = PostfixTreeBuilder.buildTree(postfix);
        
        assertTrue(root.getLeft().isLeaf());
        assertTrue(root.getRight().isLeaf());
    }
    
    @Test
    @DisplayName("should create operator nodes correctly")
    void testOperatorNodes() {
        List<String> postfix = List.of("a", "b", "+", "c", "*");
        TreeNode root = PostfixTreeBuilder.buildTree(postfix);
        
        assertTrue(root.isOperator());
        assertTrue(root.getLeft().isOperator());
    }
    
    @Test
    @DisplayName("should throw exception for insufficient operands")
    void testInsufficientOperands() {
        List<String> postfix = List.of("3", "+");
        assertThrows(InvalidEquationException.class, () -> PostfixTreeBuilder.buildTree(postfix));
    }
    
    @Test
    @DisplayName("should throw exception for too many operands")
    void testTooManyOperands() {
        List<String> postfix = List.of("3", "2", "+", "5");
        assertThrows(InvalidEquationException.class, () -> PostfixTreeBuilder.buildTree(postfix));
    }
    
    @Test
    @DisplayName("should throw exception for null tokens")
    void testNullTokens() {
        assertThrows(InvalidEquationException.class, () -> PostfixTreeBuilder.buildTree(null));
    }
    
    @Test
    @DisplayName("should throw exception for empty tokens")
    void testEmptyTokens() {
        assertThrows(InvalidEquationException.class, () -> PostfixTreeBuilder.buildTree(List.of()));
    }
}
