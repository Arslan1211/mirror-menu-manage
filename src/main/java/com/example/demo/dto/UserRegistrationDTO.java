package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegistrationDTO {
    @NotBlank
    @Size(min = 3, max = 25)
    private String username;

    @NotBlank
    @Size(min = 3)
    private String password;
}
