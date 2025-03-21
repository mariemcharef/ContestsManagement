package com.cp.Contests_management.TestCase;


import com.cp.Contests_management.Competition.Competition;
import com.cp.Contests_management.Problem.Problem;
import com.cp.Contests_management.Submission.Submission;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TestCase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(
            nullable = false
    )
    private String input;
    @Column(
            nullable = false
    )
    private String output;
    private  boolean hidden=false;
    private String explanation;
    @ManyToOne
    @JoinColumn(name="problem_id",nullable = false)
    private Problem problem;


}
