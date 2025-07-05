package com.example.demo.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UsernameExistsException extends RuntimeException {
    public UsernameExistsException(String username) {
        super(String.format("Username %s already exists", username));
    }
}
