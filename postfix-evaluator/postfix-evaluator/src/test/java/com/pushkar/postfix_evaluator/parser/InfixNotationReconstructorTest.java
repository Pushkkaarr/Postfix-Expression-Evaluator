package com.pushkar.postfix_evaluator.parser;

import com.pushkar.postfix_evaluator.model.TreeNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for InfixNotationReconstructor
 */
@DisplayName("Infix Notation Reconstructor Tests")
class InfixNotationReconstructorTest {
    
    private TreeNode root;
    
    @Test
    @DisplayName("should reconstruct simple addition")
    void testSimpleAddition() {
        // Tree: 3 + 2
        TreeNode root = new TreeNode();
        root.setValue("+");
        root.setLeft(new TreeNode("3", null, null));
        root.setRight(new TreeNode("2", null, null));
        
        String result = InfixNotationReconstructor.reconstruct(root);
        assertEquals("3+2", result);
    }
    
    @Test
    @DisplayName("should reconstruct expression with operator precedence")
    void testOperatorPrecedence() {
        // Tree: 3 + 2 * x (from postfix: 3 2 x * +)
        TreeNode root = new TreeNode();
        root.setValue("+");
        root.setLeft(new TreeNode("3", null, null));
        
        TreeNode multNode = new TreeNode();
        multNode.setValue("*");
        multNode.setLeft(new TreeNode("2", null, null));
        multNode.setRight(new TreeNode("x", null, null));
        root.setRight(multNode);
        
        String result = InfixNotationReconstructor.reconstruct(root);
        assertEquals("3+2*x", result);
    }
    
    @Test
    @DisplayName("should add parentheses when needed for lower precedence")
    void testParenthesesForLowerPrecedence() {
        // Tree: (3 + 2) * x
        TreeNode root = new TreeNode();
        root.setValue("*");
        
        TreeNode addNode = new TreeNode();
        addNode.setValue("+");
        addNode.setLeft(new TreeNode("3", null, null));
        addNode.setRight(new TreeNode("2", null, null));
        root.setLeft(addNode);
        
        root.setRight(new TreeNode("x", null, null));
        
        String result = InfixNotationReconstructor.reconstruct(root);
        assertEquals("(3+2)*x", result);
    }
    
    @Test
    @DisplayName("should handle power operator")
    void testPowerOperator() {
        // Tree: x ^ 2
        TreeNode root = new TreeNode();
        root.setValue("^");
        root.setLeft(new TreeNode("x", null, null));
        root.setRight(new TreeNode("2", null, null));
        
        String result = InfixNotationReconstructor.reconstruct(root);
        assertEquals("x^2", result);
    }
    
    @Test
    @DisplayName("should handle division requiring parentheses")
    void testDivisionParentheses() {
        // Tree: x / (y / z)
        TreeNode root = new TreeNode();
        root.setValue("/");
        root.setLeft(new TreeNode("x", null, null));
        
        TreeNode divNode = new TreeNode();
        divNode.setValue("/");
        divNode.setLeft(new TreeNode("y", null, null));
        divNode.setRight(new TreeNode("z", null, null));
        root.setRight(divNode);
        
        String result = InfixNotationReconstructor.reconstruct(root);
        assertEquals("x/(y/z)", result);
    }
    
    @Test
    @DisplayName("should handle null node")
    void testNullNode() {
        String result = InfixNotationReconstructor.reconstruct(null);
        assertEquals("", result);
    }
    
    @Test
    @DisplayName("should reconstruct single operand")
    void testSingleOperand() {
        TreeNode leaf = new TreeNode("x", null, null);
        String result = InfixNotationReconstructor.reconstruct(leaf);
        assertEquals("x", result);
    }
}
