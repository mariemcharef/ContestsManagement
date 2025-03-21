package com.cp.Contests_management.AppUser;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class AppUserDTO {
    private String name;
    private String email;
    private int rating;
}
