package com.example.demo.exeption;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ErrorResponse {
    private int status;
    private Date timestamp;
    private String message;
    private String details;

    public ErrorResponse(int status, Date timestamp, String message, String details) {
        this.status = status;
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }
}
