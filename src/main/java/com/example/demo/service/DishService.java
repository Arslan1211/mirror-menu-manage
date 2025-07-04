package com.example.demo.service;

import com.example.demo.entity.Dish;
import com.example.demo.entity.User;
import com.example.demo.exeption.DishNotFoundException;
import com.example.demo.repository.DishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DishService {
    private final DishRepository dishRepository;

    public List<Dish> getAllDishes() {
        return dishRepository.findAll();
    }

    public Dish createDish(Dish dish, User user) {
        dish.setCreatedBy(user.getId());
        return dishRepository.save(dish);
    }

    public Dish updateDish(Long dishId, Dish dishDetails, User user) {
        Dish dish = dishRepository.findByIdAndCreatedBy(dishId, user.getId())
                .orElseThrow(() -> new DishNotFoundException(dishId));

        dish.setName(dishDetails.getName());
        dish.setDescription(dishDetails.getDescription());
        dish.setCreatedBy(user.getId());
        dish.setPrice(dishDetails.getPrice());
        dish.setQuantity(dishDetails.getQuantity());

        return dishRepository.save(dish);
    }

    public void deleteDish(Long dishId, User user) {
        Dish dish = dishRepository.findByIdAndCreatedBy(dishId, user.getId())
                .orElseThrow(() -> new DishNotFoundException(dishId));

        dishRepository.delete(dish);
    }

    public Dish getDishById(Long dishId) {
        return dishRepository.findById(dishId)
                .orElseThrow(() -> new DishNotFoundException(dishId));
    }
}
