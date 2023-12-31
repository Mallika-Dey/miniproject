package com.example.miniproject.dto;

import com.example.miniproject.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    private String firstName;
    private String lastName;

    private String email;

    private String address;
    private Role role;
}
