package com.cp.Contests_management.TestCase;


import com.cp.Contests_management.Competition.Competition;
import com.cp.Contests_management.Problem.Problem;
import com.cp.Contests_management.Submission.Submission;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.micrometer.common.lang.Nullable;
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
    private Integer id;

    @Column(
            length = 10000,
            nullable = false
    )
    private String input;

    @Column(
            length = 10000,
            nullable = false
    )
    private String output;
    private  boolean hidden=true;
    @Nullable
    @Column(
            length = 10000
    )
    private String explanation;
    @ManyToOne
    @JoinColumn(name="problem_id",nullable = false)
    @JsonBackReference
    private Problem problem;


}
