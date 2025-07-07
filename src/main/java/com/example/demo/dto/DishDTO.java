package com.example.demo.dto;

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

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.isStopped = (quantity == 0); // Автоматически обновляем флаг
    }
}
