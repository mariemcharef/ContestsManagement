package com.cp.Contests_management.Clarification;

import com.cp.Contests_management.Competition.Competition;
import com.cp.Contests_management.Participant.Participant;
import com.cp.Contests_management.Problem.Problem;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Clarification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(
            nullable = false
    )
    private String clarification;
    @ManyToOne
    @JoinColumn(name="participant_id")
    private Participant participant;

    @ManyToOne
    @JoinColumn(name="problem_id",nullable = false)
    private Problem problem;
}
