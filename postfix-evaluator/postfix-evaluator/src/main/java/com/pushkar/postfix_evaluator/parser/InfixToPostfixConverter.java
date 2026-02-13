package com.pushkar.postfix_evaluator.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Converts infix notation to postfix (Reverse Polish Notation) using Shunting Yard algorithm
 */
public class InfixToPostfixConverter {
    
    /**
     * Converts infix tokens to postfix notation
     * 
     * @param infixTokens list of infix tokens
     * @return list of postfix tokens
     * @throws IllegalArgumentException if the expression is malformed
     */
    public static List<String> convert(List<String> infixTokens) {
        if (infixTokens == null || infixTokens.isEmpty()) {
            throw new IllegalArgumentException("Token list cannot be null or empty");
        }
        
        List<String> postfix = new ArrayList<>();
        Stack<String> operatorStack = new Stack<>();
        
        int parenDepth = 0;
        boolean expectOperand = true;
        
        for (int i = 0; i < infixTokens.size(); i++) {
            String token = infixTokens.get(i);
            
            if (Tokenizer.isNumber(token) || Tokenizer.isVariable(token)) {
                if (!expectOperand) {
                    throw new IllegalArgumentException(
                        "Unexpected operand at position " + i + ": " + token
                    );
                }
                postfix.add(token);
                expectOperand = false;
            } 
            else if (token.equals("(")) {
                if (!expectOperand) {
                    throw new IllegalArgumentException(
                        "Unexpected '(' at position " + i
                    );
                }
                operatorStack.push(token);
                parenDepth++;
                expectOperand = true;
            } 
            else if (token.equals(")")) {
                if (expectOperand) {
                    throw new IllegalArgumentException(
                        "Unexpected ')' at position " + i
                    );
                }
                parenDepth--;
                if (parenDepth < 0) {
                    throw new IllegalArgumentException(
                        "Mismatched parentheses: extra closing '(' at position " + i
                    );
                }
                
                while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")) {
                    postfix.add(operatorStack.pop());
                }
                
                if (operatorStack.isEmpty()) {
                    throw new IllegalArgumentException(
                        "Mismatched parentheses at position " + i
                    );
                }
                operatorStack.pop(); // Remove the '('
                expectOperand = false;
            } 
            else if (Tokenizer.isOperator(token)) {
                if (expectOperand) {
                    // Handle unary minus
                    if (token.equals("-") && (i == 0 || isOperatorOrOpenParen(infixTokens.get(i - 1)))) {
                        // Treat as unary minus - insert a 0
                        postfix.add("0");
                    } else {
                        throw new IllegalArgumentException(
                            "Unexpected operator at position " + i + ": " + token
                        );
                    }
                }
                
                while (!operatorStack.isEmpty() && 
                       !operatorStack.peek().equals("(") &&
                       shouldPopOperator(token, operatorStack.peek())) {
                    postfix.add(operatorStack.pop());
                }
                
                operatorStack.push(token);
                expectOperand = true;
            } 
            else {
                throw new IllegalArgumentException("Invalid token: " + token);
            }
        }
        
        if (expectOperand) {
            throw new IllegalArgumentException("Expression ends with operator");
        }
        
        if (parenDepth != 0) {
            throw new IllegalArgumentException("Mismatched parentheses: unclosed '('");
        }
        
        while (!operatorStack.isEmpty()) {
            String op = operatorStack.pop();
            if (op.equals("(") || op.equals(")")) {
                throw new IllegalArgumentException("Mismatched parentheses");
            }
            postfix.add(op);
        }
        
        return postfix;
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
    
    /**
     * Checks if an operator is right-associative
     */
    private static boolean isRightAssociative(String operator) {
        return "^".equals(operator);
    }
    
    /**
     * Determines if the operator on stack should be popped
     */
    private static boolean shouldPopOperator(String current, String onStack) {
        int currentPrec = getPrecedence(current);
        int stackPrec = getPrecedence(onStack);
        
        if (stackPrec < currentPrec) {
            return false;
        }
        if (stackPrec > currentPrec) {
            return true;
        }
        // Same precedence - check associativity
        return !isRightAssociative(current);
    }
    
    /**
     * Checks if token is an operator or opening parenthesis
     */
    private static boolean isOperatorOrOpenParen(String token) {
        return Tokenizer.isOperator(token) || token.equals("(");
    }
}
