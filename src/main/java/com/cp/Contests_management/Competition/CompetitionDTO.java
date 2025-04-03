package com.cp.Contests_management.Competition;

import com.cp.Contests_management.Problem.ProblemDTO;
import com.cp.Contests_management.User.UserDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CompetitionDTO {
    private Integer id;
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private float duration;
    private float penalty;
    private String userName;
    private List<ProblemDTO> problems;
}
