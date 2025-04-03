package com.cp.Contests_management.Participant;


import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;
@Data
public class ParticipantAddRequest {
    @NotEmpty(message="Name cannot be empty!")
    private String name;

}
