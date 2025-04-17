package com.cp.Contests_management.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
//required
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    @NotEmpty(message="Password connot be empty")
    @NotBlank(message="Password connot be empty")
    @Size(min=6,message="Password should be 8 characters long minimum")
    private String password;
    @NotEmpty(message="Email connot be empty")
    @NotBlank(message="Email connot be empty")
    @Email(message ="Email is not well formatted")
    private String email;
}
