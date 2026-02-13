package com.pushkar.postfix_evaluator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for storing equation requests
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquationRequestDTO {
    private String equation;
}
