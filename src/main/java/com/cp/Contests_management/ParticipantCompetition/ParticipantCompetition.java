package com.cp.Contests_management.ParticipantCompetition;

import com.cp.Contests_management.Competition.Competition;
import com.cp.Contests_management.Participant.Participant;
import jakarta.persistence.*;

@Entity
public class ParticipantCompetition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private float score;
    private int rank;

    @ManyToOne
    @JoinColumn(name = "participant_id", nullable = false)
    private Participant  participant;


    @ManyToOne
    @JoinColumn(name = "competition_id", nullable = false)
    private Competition competition;
}
