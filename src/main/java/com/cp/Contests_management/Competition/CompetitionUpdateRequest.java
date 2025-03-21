package com.cp.Contests_management.Competition;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class CompetitionUpdateRequest {
    private String name;
    private float duration;
    private LocalDateTime startTime;
    private float penalty;
}
