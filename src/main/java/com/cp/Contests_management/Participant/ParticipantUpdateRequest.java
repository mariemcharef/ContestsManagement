package com.cp.Contests_management.Participant;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ParticipantUpdateRequest {
    @NotEmpty(message="Name cannot be empty!")
    private String name;

}
