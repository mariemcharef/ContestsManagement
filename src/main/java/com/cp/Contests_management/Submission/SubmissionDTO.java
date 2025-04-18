package com.cp.Contests_management.Submission;

import com.cp.Contests_management.Participant.Participant;
import com.cp.Contests_management.Participant.ParticipantDTO;
import com.cp.Contests_management.Problem.Problem;
import com.cp.Contests_management.Problem.ProblemDTO;
import com.cp.Contests_management.Problem.ProblemName;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;
@Data

public class SubmissionDTO {
    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime time;
    private Language language;
    private String judgement;
    private boolean isProcessed;
    private String code;
    private ProblemName problem;
}
