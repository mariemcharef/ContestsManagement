package com.cp.Contests_management.Problem;


import com.cp.Contests_management.Announcement.Announcement;
import com.cp.Contests_management.AppUser.AppUser;
import com.cp.Contests_management.Clarification.Clarification;
import com.cp.Contests_management.Competition.Competition;
import com.cp.Contests_management.Submission.Submission;
import com.cp.Contests_management.TestCase.TestCase;
import com.cp.Contests_management.Topic.Topic;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
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
    @ManyToMany
    @JoinTable(
            name= "Problem_topic",
            joinColumns = {
                    @JoinColumn(name="problem_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name="topic_id")
            }
    )
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