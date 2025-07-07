package com.example.demo.dto;

import com.example.demo.entity.enums.DishCategory;
import lombok.Data;

@Data
public class CreateDishRequest {
    private String name;
    private String description;
    private DishCategory category;
    private int price;
    private int quantity;
    private Long createdBy;
}
