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
    private String inputExplanation;
    private String outputExplanation;
    private float timeLimit;
    private float memoryLimit;
    private List<Topic> topics;

}
