package com.cp.Contests_management.AppUser;

import lombok.Data;

@Data
public class AppUserUpdateRequest {
    private String email;
    private String password;
    private int rating;
}
