package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 2, max = 25, message = "Username must be between 2 and 25 characters")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 3, message = "The password must be between 3 characters long")
    private String password;
}
