package com.example.miniproject.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
    @NotNull(message = "First name is required")
    private String firstName;

    @NotNull(message = "Last name is required")
    private String lastName;

    @Email(message = "Invalid email")
    private String email;

    @NotNull(message = "Invalid address")
    private String address;

    @NotNull(message = "Password is required")
    @Size(min = 8, message = "password should have at least 8 characters")
    @Size(max = 12, message = "password should have maximum 12 characters")
    private String password;

    @NotNull(message = "Role is required")
    private String role;
}
