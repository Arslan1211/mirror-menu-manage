package com.example.demo.controller;

import com.example.demo.dto.CreateDishRequest;
import com.example.demo.dto.DishDTO;
import com.example.demo.dto.UpdateDishRequest;
import com.example.demo.entity.User;
import com.example.demo.service.DishService;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<DishDTO>> getAllDishes() {
        return ResponseEntity.ok(dishService.getAllDishes());
    }

    @PostMapping
    public ResponseEntity<DishDTO> createDish(
            @Valid @RequestBody CreateDishRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(dishService.createDish(request, user));
    }

    @GetMapping("/{dishId}")
    public ResponseEntity<DishDTO> getDish(@PathVariable("dishId") Long dishId) {
        return ResponseEntity.ok(dishService.getDishById(dishId));
    }

    @PutMapping("/{dishId}")
    public ResponseEntity<DishDTO> updateDish(
            @PathVariable("dishId") Long dishId,
            @Valid @RequestBody UpdateDishRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(dishService.updateDish(dishId, request, user));
    }

    @DeleteMapping("/{dishId}")
    public ResponseEntity<Void> deleteDish(
            @PathVariable Long dishId,
            @AuthenticationPrincipal User user) {
        dishService.deleteDish(dishId, user);
        return ResponseEntity.noContent().build();
    }
}