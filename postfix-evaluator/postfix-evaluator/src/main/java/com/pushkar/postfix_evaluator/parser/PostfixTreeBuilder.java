package com.pushkar.postfix_evaluator.parser;

import com.pushkar.postfix_evaluator.exception.InvalidEquationException;
import com.pushkar.postfix_evaluator.model.TreeNode;
import java.util.List;
import java.util.Stack;

/**
 * Builds an expression tree from postfix notation
 * Operators are parent nodes; operands are leaf nodes
 */
public class PostfixTreeBuilder {
    
    /**
     * Builds an expression tree from postfix tokens
     * 
     * @param postfixTokens list of tokens in postfix notation
     * @return root node of the expression tree
     * @throws InvalidEquationException if the postfix expression is invalid
     */
    public static TreeNode buildTree(List<String> postfixTokens) {
        if (postfixTokens == null || postfixTokens.isEmpty()) {
            throw new InvalidEquationException("Postfix token list cannot be null or empty");
        }
        
        Stack<TreeNode> nodeStack = new Stack<>();
        
        for (String token : postfixTokens) {
            if (Tokenizer.isNumber(token) || Tokenizer.isVariable(token)) {
                // Create leaf node for operand
                TreeNode node = new TreeNode();
                node.setValue(token);
                node.setLeft(null);
                node.setRight(null);
                nodeStack.push(node);
            } 
            else if (Tokenizer.isOperator(token)) {
                // Create operator node with children
                if (nodeStack.size() < 2) {
                    throw new InvalidEquationException(
                        "Invalid postfix expression: insufficient operands for operator '" + token + "'"
                    );
                }
                
                TreeNode right = nodeStack.pop();
                TreeNode left = nodeStack.pop();
                
                TreeNode operatorNode = new TreeNode();
                operatorNode.setValue(token);
                operatorNode.setLeft(left);
                operatorNode.setRight(right);
                
                nodeStack.push(operatorNode);
            } 
            else {
                throw new InvalidEquationException("Invalid token in postfix expression: " + token);
            }
        }
        
        if (nodeStack.size() != 1) {
            throw new InvalidEquationException(
                "Invalid postfix expression: expected single root node, but got " + nodeStack.size()
            );
        }
        
        return nodeStack.pop();
    }
}
