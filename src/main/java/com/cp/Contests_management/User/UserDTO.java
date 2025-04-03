package com.cp.Contests_management.User;

import com.cp.Contests_management.Competition.CompetitionDTO;
import com.cp.Contests_management.Participant.ParticipantDTO;
import lombok.Data;

import java.util.List;

@Data

public class UserDTO {
    private Integer id;
    private String name;
    private String email;
    private int rating;
    //private List<CompetitionDTO> competitions;
    //private List<ParticipantDTO> participants;
}
