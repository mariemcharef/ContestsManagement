package com.cp.Contests_management.Clarification;

import com.cp.Contests_management.Participant.Participant;
import com.cp.Contests_management.Participant.ParticipantDTO;
import com.cp.Contests_management.Problem.ProblemDTO;
import lombok.Data;



@Data

public class ClarificationDTO {
    private Integer id;
    private String question;
    private String answer;
    private ParticipantDTO participant;
}
