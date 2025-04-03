package com.cp.Contests_management.ParticipantCompetition;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ParticipantCompetitionDTO {
    @NotEmpty(message = "You should provide the participant Name")
    private String participantName;
    @NotEmpty(message = "You should provide the competition Name")
    private String competitionName;
}
