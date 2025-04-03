package com.cp.Contests_management.ParticipantCompetition;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantsCompetitionsId implements Serializable {

    private Integer participantId;
    private Integer competitionId;



    // Explicitly implement equals() and hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticipantsCompetitionsId that = (ParticipantsCompetitionsId) o;
        return Objects.equals(participantId, that.participantId) &&
                Objects.equals(competitionId, that.competitionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(participantId, competitionId);
    }
}