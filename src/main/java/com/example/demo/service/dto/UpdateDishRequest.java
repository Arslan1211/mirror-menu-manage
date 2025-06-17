package com.example.demo.service.dto;

import com.example.demo.entity.enums.DishCategory;
import lombok.Data;

@Data
public class UpdateDishRequest {
    private String name;
    private String description;
    private DishCategory category;
    private int price;
    private int quantity;
}
