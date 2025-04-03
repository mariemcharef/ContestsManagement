package com.cp.Contests_management.Problem;

import com.cp.Contests_management.TestCase.TestCase;
import lombok.Data;

import java.util.List;
@Data

public class ProblemDTO {
    private Integer id;
    private String name;
    private String label;
    private String content;
    private float timeLimit;
    private float memoryLimit;
    private List<Topic> topics;
   // private Integer competitionId;
    //private List<TestCase> testCases;
}
