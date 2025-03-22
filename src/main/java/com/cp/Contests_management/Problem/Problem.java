package com.cp.Contests_management.Problem;


import com.cp.Contests_management.Clarification.Clarification;
import com.cp.Contests_management.Competition.Competition;
import com.cp.Contests_management.Submission.Submission;
import com.cp.Contests_management.TestCase.TestCase;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(
       length =1,
       nullable = false
    )
    private String label;
    @Column(nullable = false)
    private String content;
    private float timeLimit;//en melliseconds
    private float memoryLimit;//en megabytes



    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "problem_topics", joinColumns = @JoinColumn(name = "problem_id"))
    @Column(name = "topic")
    private List<Topic> topics;



    @OneToMany(mappedBy = "problem")
    private List<Clarification> clarifications;

    @ManyToOne
    @JoinColumn(name="competition_id",nullable = false)
    private Competition competition;

    @OneToMany(mappedBy = "problem")
    private List<Submission> submissions;

    @OneToMany(mappedBy = "problem")
    private List<TestCase> testCases;

}