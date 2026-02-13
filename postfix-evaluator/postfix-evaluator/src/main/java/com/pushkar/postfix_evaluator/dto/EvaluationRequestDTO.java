package com.pushkar.postfix_evaluator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * DTO for evaluation requests containing variable values
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationRequestDTO {
    private Map<String, Number> variables;
}
