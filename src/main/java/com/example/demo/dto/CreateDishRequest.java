package com.example.demo.dto;

import com.example.demo.entity.enums.DishCategory;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateDishRequest {
    @NotBlank(message = "Dish name cannot be blank")
    @Size(min = 2, max = 25, message = "Dish name must be between 2 and 25 characters")
    private String name;

    @Size(max = 100, message = "Description cannot exceed 100 characters")
    private String description;

    @NotNull(message = "Category cannot be null")
    @Enumerated(EnumType.STRING)
    private DishCategory category;

    @Min(value = 0, message = "Price cannot be negative")
    private int price;

    @Min(value = 0, message = "Quantity cannot be negative")
    private int quantity;

    @Positive(message = "Creator ID must be a positive number")
    private Long createdBy;
}
