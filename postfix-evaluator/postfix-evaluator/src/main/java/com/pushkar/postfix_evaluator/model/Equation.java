package com.pushkar.postfix_evaluator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model class representing a stored equation with its tree structure
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Equation {
    private String id;
    
    private String equationInfix;
    
    private TreeNode rootNode;
}
