package com.pushkar.postfix_evaluator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * DTO for evaluation responses containing results and details
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationResponseDTO {
    @JsonProperty("equationId")
    private String equationId;
    
    private String equation;
    
    private Map<String, Number> variables;
    
    private Double result;
}
