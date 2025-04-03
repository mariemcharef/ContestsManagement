package com.cp.Contests_management.Clarification;

import com.cp.Contests_management.Participant.Participant;
import com.cp.Contests_management.Problem.Problem;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ClarificationRequest {
    @NotEmpty(message="clarification connot be empty")
    private String clarification;

}
