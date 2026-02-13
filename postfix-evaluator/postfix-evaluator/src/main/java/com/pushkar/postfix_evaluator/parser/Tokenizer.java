package com.pushkar.postfix_evaluator.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Tokenizes an algebraic equation into individual tokens
 */
public class Tokenizer {
    private static final Pattern TOKEN_PATTERN = Pattern.compile(
            "\\d+\\.?\\d*|[a-zA-Z_][a-zA-Z0-9_]*|[+\\-*^/()]"
    );
    
    /**
     * Tokenizes the given equation string
     * 
     * @param equation the equation string to tokenize
     * @return list of tokens
     * @throws IllegalArgumentException if the equation contains invalid characters
     */
    public static List<String> tokenize(String equation) {
        if (equation == null || equation.trim().isEmpty()) {
            throw new IllegalArgumentException("Equation cannot be null or empty");
        }
        
        List<String> tokens = new ArrayList<>();
        equation = equation.replaceAll("\\s+", "");
        
        Matcher matcher = TOKEN_PATTERN.matcher(equation);
        int lastEnd = 0;
        
        while (matcher.find()) {
            // Check for gaps (invalid characters)
            if (matcher.start() > lastEnd) {
                throw new IllegalArgumentException(
                    "Invalid character at position " + lastEnd + ": '" + 
                    equation.charAt(lastEnd) + "'"
                );
            }
            tokens.add(matcher.group());
            lastEnd = matcher.end();
        }
        
        // Check if entire string was successfully parsed
        if (lastEnd < equation.length()) {
            throw new IllegalArgumentException(
                "Invalid character at position " + lastEnd + ": '" + 
                equation.charAt(lastEnd) + "'"
            );
        }
        
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("No valid tokens found in equation");
        }
        
        return tokens;
    }
    
    /**
     * Checks if a token is a number
     */
    public static boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Checks if a token is a variable (letter/identifier)
     */
    public static boolean isVariable(String token) {
        return token != null && token.matches("[a-zA-Z_][a-zA-Z0-9_]*");
    }
    
    /**
     * Checks if a token is an operator
     */
    public static boolean isOperator(String token) {
        return token != null && (token.equals("+") || token.equals("-") || 
                token.equals("*") || token.equals("/") || token.equals("^"));
    }
}
