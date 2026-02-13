package com.pushkar.postfix_evaluator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for equation response containing message and ID
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquationResponseDTO {
    private String message;
    
    @JsonProperty("equationId")
    private String equationId;
}
