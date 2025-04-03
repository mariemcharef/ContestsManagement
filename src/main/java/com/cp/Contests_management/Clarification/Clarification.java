package com.cp.Contests_management.Clarification;

import com.cp.Contests_management.Competition.Competition;
import com.cp.Contests_management.Participant.Participant;
import com.cp.Contests_management.Problem.Problem;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class  Clarification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(
            nullable = false
    )
    private String clarification;
    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name="participant_id")
    private Participant participant;

    @ManyToOne
    @JoinColumn(name="problem_id",nullable = false)
    @JsonManagedReference
    private Problem problem;
}
