package com.example.demo.exeption;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class ApiError {
    private String message;
    private LocalDateTime timestamp;
    private String path;
    private Map<String, String> fieldErrors;
}