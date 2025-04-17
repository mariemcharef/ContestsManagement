package com.cp.Contests_management.Submission;

import com.cp.Contests_management.Participant.Participant;
import com.cp.Contests_management.Problem.Problem;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.Unmodifiable;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(nullable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime time= LocalDateTime.now();

    @Column(nullable = false)//CPP by default
    private Language language=Language.CPP;

    private String judge0Token;

    @Column(nullable = false)
    private String judgement="In Queue";


    private boolean isProcessed=false;

    @Column(
            length = 10000,
            nullable = false
    )
    private String code;

    @ManyToOne
    @JoinColumn(name="participant_id")
    @JsonManagedReference
    private Participant participant;

    @ManyToOne
    @JoinColumn(name="problem_id")
    @JsonManagedReference
    private Problem problem;

}