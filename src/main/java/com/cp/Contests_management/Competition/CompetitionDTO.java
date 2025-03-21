package com.cp.Contests_management.Competition;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class CompetitionDTO {
    private Long id;
    private String name;
    private float duration;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private float penalty;
    private Long appUserId;
}
