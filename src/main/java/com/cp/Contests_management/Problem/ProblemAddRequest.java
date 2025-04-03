package com.cp.Contests_management.Problem;

import com.cp.Contests_management.Clarification.Clarification;
import com.cp.Contests_management.Competition.Competition;
import com.cp.Contests_management.TestCase.TestCase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class ProblemAddRequest {
    @NotEmpty(message = "You must provide a problem name")
    @Size(min = 4,message = "problem name size must be at least containing 4 characters")
    private String name;
    @NotEmpty(message="Label cannot be empty")
    private String label;
    @NotEmpty(message="content cannot be Empty")
    private String content;
    @NotNull(message="timelimit cannot be null")
    private float timeLimit;
    @NotNull(message="timelimit cannot be null")
    private float memoryLimit;

    private Set<Topic> topics;
    //private Integer competitionId;
    // private List<TestCaseRequest> testCases;
}
