package com.cp.Contests_management.AppUser;

import com.cp.Contests_management.Participant.Participant;
import com.cp.Contests_management.Participant.ParticipantDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data

public class AppUserDTO {
    private String name;
    private String email;
    private int rating;
    private List<ParticipantDTO> participants;
}
