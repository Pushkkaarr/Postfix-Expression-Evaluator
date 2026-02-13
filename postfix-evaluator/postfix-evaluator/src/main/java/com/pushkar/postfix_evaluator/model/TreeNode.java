package com.pushkar.postfix_evaluator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a node in the expression tree
 * Operators are parent nodes, operands are leaf nodes
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreeNode {
    private String value;
    
    private TreeNode left;
    
    private TreeNode right;
    
    /**
     * Checks if this node is a leaf node (operand)
     */
    public boolean isLeaf() {
        return left == null && right == null;
    }
    
    /**
     * Checks if the value is an operator
     */
    public boolean isOperator() {
        return value != null && (value.equals("+") || value.equals("-") || 
                value.equals("*") || value.equals("/") || value.equals("^"));
    }
}
