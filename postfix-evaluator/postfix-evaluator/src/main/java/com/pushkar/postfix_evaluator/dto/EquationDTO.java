package com.pushkar.postfix_evaluator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for displaying stored equations
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquationDTO {
    @JsonProperty("equationId")
    private String equationId;
    
    private String equation;
}
