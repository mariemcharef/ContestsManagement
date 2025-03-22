package com.cp.Contests_management.Problem;

import com.cp.Contests_management.Clarification.Clarification;
import com.cp.Contests_management.Competition.Competition;
import com.cp.Contests_management.TestCase.TestCase;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Data
public class ProblemAddRequest {
    private String name;
    private String label;
    private String content;
    private float timeLimit;
    private float memoryLimit;
    private List<Topic> topics;
    private Long competitionId;
   // private List<TestCaseRequest> testCases;
}
