package com.example.demo.controller;

import com.example.demo.entity.Dish;
import com.example.demo.service.DishService;
import com.example.demo.service.dto.CreateDishRequest;
import com.example.demo.service.dto.DishDTO;
import com.example.demo.service.dto.UpdateDishRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dishes")
public class DishController {
    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping
    public List<DishDTO> getDishes() {
        return dishService.findAll();
    }

    @GetMapping("/{dishId}")
    public Dish getDish(@PathVariable("dishId") Long dishId) {
        return dishService.getDishById(dishId);
    }

    @PostMapping
    public DishDTO createDish(@RequestBody CreateDishRequest createDishRequest) {
        return dishService.createDish(createDishRequest);
    }

    @PutMapping("/{id}")
    public DishDTO updateDish(@PathVariable Long id,
                              @RequestBody UpdateDishRequest updateDishRequest) {
        return dishService.updateDish(id, updateDishRequest);
    }

    @DeleteMapping("/{dishId}")
    public void deleteDish(@PathVariable("dishId") Long dishId) {
        dishService.deleteDish(dishId);
    }
}
