package com.cp.Contests_management.Problem;


import com.cp.Contests_management.Clarification.Clarification;
import com.cp.Contests_management.Competition.Competition;
import com.cp.Contests_management.Submission.Submission;
import com.cp.Contests_management.TestCase.TestCase;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;
    @Column(
       length =1,
       nullable = false
    )
    private String label;

    @Column(length = 10000,nullable = false)
    private String content;
    private float timeLimit;//en melliseconds
    private float memoryLimit;//en megabytes

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "problem_topics", joinColumns = @JoinColumn(name = "problem_id"))
    @Column(name = "topic")
    private Set<Topic> topics;


    @OneToMany(mappedBy = "problem")
    @JsonBackReference
    private List<Clarification> clarifications;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name="competition_id",nullable = false)
    private Competition competition;

    @OneToMany(mappedBy = "problem")
    @JsonBackReference
    private List<Submission> submissions;

    @OneToMany(mappedBy = "problem")
    @JsonManagedReference
    private List<TestCase> testCases;

}