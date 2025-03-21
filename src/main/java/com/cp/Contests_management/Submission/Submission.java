package com.cp.Contests_management.Submission;

import com.cp.Contests_management.Participant.Participant;
import com.cp.Contests_management.Problem.Problem;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime time= LocalDateTime.now();
    @Column(nullable = false)
    private Language language;

    @Column(nullable = false)
    private Judgement judgement;

    @Column(nullable = false)
    private String code;

    @ManyToOne
    @JoinColumn(name="participant_id")
    private Participant participant;

    @ManyToOne
    @JoinColumn(name="problem_id")
    private Problem problem;
}