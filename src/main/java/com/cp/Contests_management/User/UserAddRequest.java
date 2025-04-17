package com.cp.Contests_management.User;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserAddRequest {
    @NotEmpty(message="Name cannot be empty!")
    private String name;

    @Email(message="Please enter a valid email!")
    @NotEmpty(message="Email cannot be empty!")
    private String email;

    @NotEmpty(message = "Password is required. Please choose a strong password.")
    @Size(min = 8, message = "Password must be at least 6 characters long.")
    private String password;

}
