package com.example.demo.exeption;

public class UnauthorizedEditException extends RuntimeException {
    public UnauthorizedEditException(String message) {
        super("You are not authorized");
    }
}
