package com.cp.Contests_management.Participant;

import com.cp.Contests_management.AppUser.AppUser;
import com.cp.Contests_management.AppUser.AppUserDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Data
public class ParticipantAddRequest {
    private String name;

    private Integer userCount;

    private List<String> AppUsers;
}
