package com.cp.Contests_management.TestCase;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class TestCaseRequest {
    @NotEmpty(message = "Input cannot be vide")
    private String input;
    @NotEmpty(message = "Output cannot be vide")
    private String output;

    private boolean hidden;

    private String explanation;

}
