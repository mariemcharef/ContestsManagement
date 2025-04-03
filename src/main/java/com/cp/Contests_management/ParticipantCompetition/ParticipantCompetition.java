package com.cp.Contests_management.ParticipantCompetition;

import com.cp.Contests_management.Competition.Competition;
import com.cp.Contests_management.Participant.Participant;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
public class ParticipantCompetition {
    @EmbeddedId
    private ParticipantsCompetitionsId id;

    private float score=0;
    private int rank;

    @ManyToOne
    @JoinColumn(name = "participant_id", nullable = false)
    @MapsId("participantId")
    private Participant  participant;


    @ManyToOne
    @JoinColumn(name = "competition_id", nullable = false)
    @MapsId("competitionId")//used to establish a shared primary key relationship between two entities
    private Competition competition;
}
