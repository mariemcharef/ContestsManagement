package com.cp.Contests_management.Competition;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class CompetitionUpdateRequest {


    @Size(min = 4,message = "Competition name size must be at least containing 4 characters")
    private String name;

    private float duration;

    private LocalDateTime startTime;

    private float penalty;
}
