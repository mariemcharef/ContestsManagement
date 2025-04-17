package com.cp.Contests_management.Clarification;

import com.cp.Contests_management.Participant.Participant;
import com.cp.Contests_management.Problem.Problem;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class ClarificationRequest {

    @NotEmpty(message="Question cannot be empty")
    private String question;

}
