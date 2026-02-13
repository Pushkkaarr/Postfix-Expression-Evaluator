package com.pushkar.postfix_evaluator.service;

import com.pushkar.postfix_evaluator.exception.EvaluationException;
import com.pushkar.postfix_evaluator.exception.InvalidEquationException;
import com.pushkar.postfix_evaluator.model.TreeNode;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Service for evaluating expression trees with variable values
 */
public class EvaluatorService {
    
    /**
     * Evaluates an expression tree with the given variable values
     * 
     * @param rootNode the root of the expression tree
     * @param variables map of variable names to their numeric values
     * @return the result of evaluation
     * @throws EvaluationException if evaluation fails
     */
    public static Double evaluate(TreeNode rootNode, Map<String, Number> variables) {
        if (rootNode == null) {
            throw new EvaluationException("Expression tree is null");
        }
        
        if (variables == null) {
            throw new InvalidEquationException("Variables map cannot be null");
        }
        
        try {
            return evaluateNode(rootNode, variables);
        } catch (EvaluationException e) {
            throw e;
        } catch (Exception e) {
            throw new EvaluationException("Error during evaluation: " + e.getMessage(), e);
        }
    }
    
    /**
     * Recursively evaluates a node in the expression tree
     */
    private static Double evaluateNode(TreeNode node, Map<String, Number> variables) {
        if (node == null) {
            throw new EvaluationException("Unexpected null node during evaluation");
        }
        
        // Leaf node - return the operand value
        if (node.isLeaf()) {
            String value = node.getValue();
            
            // Try to parse as number
            try {
                return Double.parseDouble(value);
            } catch (NumberFormatException e) {
                // Try as variable
                if (variables.containsKey(value)) {
                    Number num = variables.get(value);
                    return num != null ? num.doubleValue() : 0.0;
                } else {
                    throw new EvaluationException(
                        "Variable '" + value + "' not provided in variables map"
                    );
                }
            }
        }
        
        // Operator node - evaluate both sides recursively
        Double leftValue = evaluateNode(node.getLeft(), variables);
        Double rightValue = evaluateNode(node.getRight(), variables);
        
        if (leftValue == null || rightValue == null) {
            throw new EvaluationException("Null value encountered during evaluation");
        }
        
        return applyOperator(node.getValue(), leftValue, rightValue);
    }
    
    /**
     * Applies an operator to two operands
     */
    private static Double applyOperator(String operator, Double left, Double right) {
        switch (operator) {
            case "+":
                return left + right;
            case "-":
                return left - right;
            case "*":
                return left * right;
            case "/":
                if (right == 0) {
                    throw new EvaluationException("Division by zero");
                }
                return left / right;
            case "^":
                return Math.pow(left, right);
            default:
                throw new EvaluationException("Unknown operator: " + operator);
        }
    }
    
    /**
     * Extracts all variables from an expression tree
     * 
     * @param rootNode the root of the expression tree
     * @return set of variable names
     */
    public static Set<String> extractVariables(TreeNode rootNode) {
        Set<String> variables = new HashSet<>();
        extractVariablesRecursive(rootNode, variables);
        return variables;
    }
    
    /**
     * Recursively extracts variables from tree
     */
    private static void extractVariablesRecursive(TreeNode node, Set<String> variables) {
        if (node == null) {
            return;
        }
        
        if (node.isLeaf()) {
            String value = node.getValue();
            // Check if it's not a number
            try {
                Double.parseDouble(value);
                // It's a number, skip
            } catch (NumberFormatException e) {
                // It's a variable
                variables.add(value);
            }
        } else {
            // Recursively process children
            extractVariablesRecursive(node.getLeft(), variables);
            extractVariablesRecursive(node.getRight(), variables);
        }
    }
}
