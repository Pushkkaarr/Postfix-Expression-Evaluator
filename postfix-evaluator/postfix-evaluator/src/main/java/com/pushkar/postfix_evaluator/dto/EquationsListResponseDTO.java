package com.pushkar.postfix_evaluator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for wrapping list of equations in response
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquationsListResponseDTO {
    private List<EquationDTO> equations;
}
