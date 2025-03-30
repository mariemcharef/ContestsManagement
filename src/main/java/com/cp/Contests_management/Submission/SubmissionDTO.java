package com.cp.Contests_management.Submission;

import com.cp.Contests_management.Participant.Participant;
import com.cp.Contests_management.Participant.ParticipantDTO;
import com.cp.Contests_management.Problem.Problem;
import com.cp.Contests_management.Problem.ProblemDTO;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;
@Data

public class SubmissionDTO {
    private Long id;
    private LocalDateTime time= LocalDateTime.now();

    private Language language;

    private String judge0Token;
    private String judgement;
    private boolean isProcessed;

    private String code;
    //private Long participantId;
    private ParticipantDTO participant;
    //private Long problemId;
    private ProblemDTO problem;
}
