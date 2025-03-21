package com.cp.Contests_management.AppUser;


import jakarta.persistence.Column;
import lombok.Data;

@Data
public class AppUserAddRequest {
    private String name;
    private String email;
    private String password;
    private int rating;

}
