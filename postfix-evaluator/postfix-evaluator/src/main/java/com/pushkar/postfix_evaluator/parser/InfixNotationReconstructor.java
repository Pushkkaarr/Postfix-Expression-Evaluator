package com.pushkar.postfix_evaluator.parser;

import com.pushkar.postfix_evaluator.model.TreeNode;

/**
 * Reconstructs infix notation from an expression tree
 */
public class InfixNotationReconstructor {
    
    /**
     * Reconstructs infix notation from an expression tree
     * 
     * @param node root node of the expression tree
     * @return infix notation string
     */
    public static String reconstruct(TreeNode node) {
        if (node == null) {
            return "";
        }
        
        // Leaf node - just return the value
        if (node.isLeaf()) {
            return node.getValue();
        }
        
        // Operator node - recursively reconstruct left and right
        StringBuilder sb = new StringBuilder();
        
        // Add left subtree
        String left = reconstruct(node.getLeft());
        
        // Add right subtree
        String right = reconstruct(node.getRight());
        
        // Determine if we need parentheses
        // Left side needs parentheses if it's a lower precedence operator
        if (node.getLeft() != null && node.getLeft().isOperator()) {
            int parentPrec = getPrecedence(node.getValue());
            int leftPrec = getPrecedence(node.getLeft().getValue());
            if (leftPrec < parentPrec) {
                left = "(" + left + ")";
            }
        }
        
        // Right side needs parentheses if:
        // 1. It's a lower precedence operator
        // 2. It's a minus/division and parent is minus/division (left associative)
        if (node.getRight() != null && node.getRight().isOperator()) {
            int parentPrec = getPrecedence(node.getValue());
            int rightPrec = getPrecedence(node.getRight().getValue());
            
            if (rightPrec < parentPrec) {
                right = "(" + right + ")";
            } 
            // For left-associative operators, right side needs parens if same/lower precedence
            else if (rightPrec == parentPrec && 
                     (node.getValue().equals("-") || node.getValue().equals("/"))) {
                right = "(" + right + ")";
            }
        }
        
        sb.append(left).append(node.getValue()).append(right);
        
        return sb.toString();
    }
    
    /**
     * Gets the precedence of an operator
     */
    private static int getPrecedence(String operator) {
        switch (operator) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            case "^":
                return 3;
            default:
                return 0;
        }
    }
}
