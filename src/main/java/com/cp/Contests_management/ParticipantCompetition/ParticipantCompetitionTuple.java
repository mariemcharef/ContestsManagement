package com.cp.Contests_management.ParticipantCompetition;

import com.cp.Contests_management.Competition.Competition;
import com.cp.Contests_management.Participant.Participant;
import lombok.Data;

@Data
public class ParticipantCompetitionTuple {
   private boolean exists;
   private Participant participant;
   private Competition competition;
}
