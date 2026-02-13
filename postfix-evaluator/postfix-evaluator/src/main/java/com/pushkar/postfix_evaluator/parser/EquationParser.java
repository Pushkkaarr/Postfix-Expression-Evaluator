package com.pushkar.postfix_evaluator.parser;

import com.pushkar.postfix_evaluator.exception.InvalidEquationException;
import com.pushkar.postfix_evaluator.model.TreeNode;
import java.util.List;

/**
 * Main parser that orchestrates equation parsing: tokenization, conversion, and tree building
 */
public class EquationParser {
    
    /**
     * Parses an infix equation and builds an expression tree
     * 
     * @param equation the equation in infix notation
     * @return the root node of the expression tree
     * @throws InvalidEquationException if the equation is invalid
     */
    public static TreeNode parse(String equation) {
        if (equation == null || equation.trim().isEmpty()) {
            throw new InvalidEquationException("Equation cannot be null or empty");
        }
        
        try {
            // Step 1: Tokenize the equation
            List<String> infixTokens = Tokenizer.tokenize(equation);
            
            // Validate tokens have proper structure
            validateInfixStructure(infixTokens);
            
            // Step 2: Convert to postfix notation
            List<String> postfixTokens = InfixToPostfixConverter.convert(infixTokens);
            
            // Step 3: Build expression tree from postfix notation
            TreeNode root = PostfixTreeBuilder.buildTree(postfixTokens);
            
            return root;
        } catch (InvalidEquationException e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidEquationException("Error parsing equation: " + e.getMessage(), e);
        }
    }
    
    /**
     * Validates the structure of infix tokens
     */
    private static void validateInfixStructure(List<String> tokens) {
        if (tokens.isEmpty()) {
            throw new InvalidEquationException("No tokens to parse");
        }
        
        boolean expectOperand = true;
        int parenDepth = 0;
        
        for (int i = 0; i < tokens.size(); i++) {
            String token = tokens.get(i);
            
            if (token.equals("(")) {
                if (!expectOperand) {
                    throw new InvalidEquationException(
                        "Unexpected '(' after operand at position " + i
                    );
                }
                parenDepth++;
                expectOperand = true;
            } 
            else if (token.equals(")")) {
                if (expectOperand) {
                    throw new InvalidEquationException(
                        "Unexpected ')' - missing operand at position " + i
                    );
                }
                parenDepth--;
                expectOperand = false;
            } 
            else if (Tokenizer.isNumber(token) || Tokenizer.isVariable(token)) {
                if (!expectOperand) {
                    throw new InvalidEquationException(
                        "Unexpected operand at position " + i + " - expected operator"
                    );
                }
                expectOperand = false;
            } 
            else if (Tokenizer.isOperator(token)) {
                if (expectOperand) {
                    // Only allow unary minus at beginning or after operator/paren
                    if (!token.equals("-") || (i > 0 && !tokens.get(i - 1).equals("("))) {
                        throw new InvalidEquationException(
                            "Missing operand before operator at position " + i
                        );
                    }
                }
                expectOperand = true;
            }
        }
        
        if (expectOperand && !tokens.get(tokens.size() - 1).equals(")")) {
            throw new InvalidEquationException("Expression ends with operator");
        }
        
        if (parenDepth != 0) {
            throw new InvalidEquationException("Mismatched parentheses");
        }
    }
}
