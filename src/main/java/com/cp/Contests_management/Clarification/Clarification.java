package com.cp.Contests_management.Clarification;

import com.cp.Contests_management.Competition.Competition;
import com.cp.Contests_management.Participant.Participant;
import com.cp.Contests_management.Problem.Problem;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class  Clarification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 10000)
    private String question;

    @Column(length = 10000)
    private String answer;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name="participant_id")
    private Participant participant;


    @ManyToOne
    @JoinColumn(name="problem_id",nullable = false)
    @JsonManagedReference
    private Problem problem;
}
