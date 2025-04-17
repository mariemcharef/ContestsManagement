package com.cp.Contests_management.TestCase;

import lombok.Data;

@Data
public class TestCaseDTO {
    Integer id;
    private String input;
    private String output;
    private boolean hidden;
    private String explanation;

}
