package com.cp.Contests_management.Submission;

import com.cp.Contests_management.Participant.Participant;
import com.cp.Contests_management.Problem.Problem;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class SubmissionAddRequest {
    private int languageId;

    private String code;

    private Long participantId;

    private Long problemId;
}
