package com.cp.Contests_management.TestCase;

import lombok.Data;

@Data
public class TestCaseRequest {
    private String input;
    private String output;
    private boolean hidden;
    private String explanation;
    private Long problemId;
}
