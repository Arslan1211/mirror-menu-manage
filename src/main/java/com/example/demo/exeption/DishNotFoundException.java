package com.example.demo.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class DishNotFoundException extends RuntimeException {
    public DishNotFoundException(Long dishId) {
        super(String.format("Dish not found with %d or you don't have permission", dishId));
    }
}