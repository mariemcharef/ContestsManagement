package com.cp.Contests_management.Participant;

import com.cp.Contests_management.User.UserDTO;
import lombok.Data;

import java.util.List;

@Data

public class ParticipantDTO {
    private Integer id;
    private String name;

    private List<UserDTO> users;

}
