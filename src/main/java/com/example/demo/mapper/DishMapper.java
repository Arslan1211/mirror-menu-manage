package com.example.demo.mapper;

import com.example.demo.entity.Dish;
import com.example.demo.dto.CreateDishRequest;
import com.example.demo.dto.DishDTO;
import com.example.demo.dto.UpdateDishRequest;
import org.springframework.stereotype.Component;

@Component
public class DishMapper {
    public DishDTO toDto(Dish dish) {
        DishDTO dto = new DishDTO();
        dto.setId(dish.getId());
        dto.setName(dish.getName());
        dto.setDescription(dish.getDescription());
        dto.setCategory(dish.getCategory());
        dto.setPrice(dish.getPrice());
        dto.setStopped(dish.isStopped());
        dto.setQuantity(dish.getQuantity());
        dto.setCreatedAt(dish.getCreatedAt());
        dto.setCreatedBy(dish.getCreatedBy());
        return dto;
    }

    public Dish toEntity(CreateDishRequest request) {
        Dish dish = new Dish();
        dish.setName(request.getName());
        dish.setDescription(request.getDescription());
        dish.setCategory(request.getCategory());
        dish.setPrice(request.getPrice());
        dish.setQuantity(request.getQuantity());
        return dish;
    }

    public void updateEntityFromRequest(UpdateDishRequest request, Dish dish) {
        if (request.getName() != null) {
            dish.setName(request.getName());
        }
        if (request.getDescription() != null) {
            dish.setDescription(request.getDescription());
        }
        if (request.getCategory() != null) {
            dish.setCategory(request.getCategory());
        }
        dish.setPrice(request.getPrice());
        dish.setQuantity(request.getQuantity());
    }
}
