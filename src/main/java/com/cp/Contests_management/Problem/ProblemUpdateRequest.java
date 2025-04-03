package com.cp.Contests_management.Problem;

import com.cp.Contests_management.TestCase.TestCase;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class ProblemUpdateRequest {
    private String name;
    private String label;
    private String content;
    private float timeLimit;
    private float memoryLimit;
    private Set<Topic> topics;

    //private List<TestCase> testCases;
}
