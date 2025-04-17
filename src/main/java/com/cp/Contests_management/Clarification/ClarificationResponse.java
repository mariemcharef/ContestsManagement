package com.cp.Contests_management.Clarification;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data

public class ClarificationResponse {

    @NotEmpty(message="Answer cannot be empty")
    private String answer;
}
