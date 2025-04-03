package com.cp.Contests_management.Competition;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class CompetitionAddRequest {
    @NotEmpty(message = "You must provide a competition name")
    @Size(min = 4,message = "Competition name size must be at least containing 4 characters")
    private String name;

    @Min(value = 30,message = "Competition duration must be at least 30 minutes long")
    private float duration;

    @NotNull(message = "You must provide starting time for the competition")
    private LocalDateTime startTime;

    private float penalty;

}
