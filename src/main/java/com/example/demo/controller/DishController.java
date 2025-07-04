package com.example.demo.controller;

import com.example.demo.entity.Dish;
import com.example.demo.entity.User;
import com.example.demo.service.DishService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dishes")
@RequiredArgsConstructor
public class DishController {
    private final DishService dishService;

    @GetMapping
    public ResponseEntity<List<Dish>> getAllDishes() {
        return ResponseEntity.ok(dishService.getAllDishes());
    }

    @PostMapping
    public ResponseEntity<Dish> createDish(@RequestBody Dish dish, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(dishService.createDish(dish, user));
    }

    @GetMapping("/{dishId}")
    public Dish getDish(@PathVariable("dishId") Long dishId) {
        return dishService.getDishById(dishId);
    }

    @PutMapping("/{dishId}")
    public ResponseEntity<Dish> updateDish(
            @PathVariable("dishId") Long dishId,
            @RequestBody Dish dish,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(dishService.updateDish(dishId, dish, user));
    }

    @DeleteMapping("/{dishId}")
    public ResponseEntity<Void> deleteDish(@PathVariable Long dishId, @AuthenticationPrincipal User user) {
        dishService.deleteDish(dishId, user);
        return ResponseEntity.noContent().build();
    }
}