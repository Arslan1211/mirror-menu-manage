package com.example.demo.service.dto;

import com.example.demo.entity.enums.DishCategory;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DishDTO {
    private Long id;
    private String name;
    private String description;
    private DishCategory category;
    private int price;
    private int quantity;
    private boolean isStopped;
    private LocalDateTime createdAt;
    private Long createdBy;
}
