package com.example.demo.service;

import com.example.demo.entity.Dish;
import com.example.demo.entity.User;
import com.example.demo.exeption.DishNotFoundException;
import com.example.demo.mapper.DishMapper;
import com.example.demo.repository.DishRepository;
import com.example.demo.dto.CreateDishRequest;
import com.example.demo.dto.DishDTO;
import com.example.demo.dto.UpdateDishRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DishService {
    private final DishRepository dishRepository;
    private final DishMapper dishMapper;

    public List<DishDTO> getAllDishes() {
        return dishRepository.findAll().stream()
                .map(dishMapper::toDto)
                .toList();
    }

    public DishDTO createDish(CreateDishRequest request, User user) {
        Dish dish = dishMapper.toEntity(request);
        dish.setCreatedBy(user.getId());
        dish.setCreatedAt(LocalDateTime.now());
        dish.setStopped(false);
        Dish savedDish = dishRepository.save(dish);
        return dishMapper.toDto(savedDish);
    }

    public DishDTO updateDish(Long dishId, UpdateDishRequest request, User user) {
        Dish dish = dishRepository.findByIdAndCreatedBy(dishId, user.getId())
                .orElseThrow(() -> new DishNotFoundException(dishId));

        dishMapper.updateEntityFromRequest(request, dish);
        Dish updatedDish = dishRepository.save(dish);
        return dishMapper.toDto(updatedDish);
    }

    public void deleteDish(Long dishId, User user) {
        Dish dish = dishRepository.findByIdAndCreatedBy(dishId, user.getId())
                .orElseThrow(() -> new DishNotFoundException(dishId));

        dishRepository.delete(dish);
    }

    public DishDTO getDishById(Long dishId) {
        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new DishNotFoundException(dishId));
        return dishMapper.toDto(dish);
    }
}