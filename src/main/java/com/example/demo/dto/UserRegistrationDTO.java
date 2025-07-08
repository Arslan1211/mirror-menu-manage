package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegistrationDTO {
    @NotBlank(message = "Name cannot be empty")
    @Size(min = 2, max = 50, message = "The name must be between 2 and 50 characters")
    private String username;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 3, message = "Password must contain 3 or more characters")
    private String password;
}
