package com.cp.Contests_management.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateRequest {
    @NotEmpty(message = "Password is required. Please choose a strong password.")
    @Size(min = 6, message = "Password must be at least 6 characters long.")
    private String password;
    private int rating;
}
