package com.cp.Contests_management.User;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PasswordChangeRequest {
    private String currentPassword;
    @Size(min = 6, message = "Password must be at least 6 characters long.")
    private String newPassword;
}
