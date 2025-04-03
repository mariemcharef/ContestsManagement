package com.cp.Contests_management.ParticipantCompetition;

import lombok.Data;

@Data
public class ParticipantCompetitionResponseDto {
    private ParticipantsCompetitionsId id;
    private String participantName;
    private String competitionName;
    private int rank;
}
