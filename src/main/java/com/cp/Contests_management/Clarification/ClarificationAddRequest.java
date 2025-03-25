package com.cp.Contests_management.Clarification;

import com.cp.Contests_management.Participant.Participant;
import com.cp.Contests_management.Problem.Problem;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class ClarificationAddRequest {
    private String clarification;

    private Long participantId;

    private Long problemId;
}
