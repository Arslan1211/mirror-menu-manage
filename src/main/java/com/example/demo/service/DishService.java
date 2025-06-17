package com.example.demo.service;

import com.example.demo.entity.Dish;
import com.example.demo.exeption.DishNotFoundException;
import com.example.demo.exeption.UnauthorizedEditException;
import com.example.demo.repository.DishRepository;
import com.example.demo.service.dto.CreateDishRequest;
import com.example.demo.service.dto.DishDTO;
import com.example.demo.service.dto.UpdateDishRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DishService {
    private final DishRepository dishRepository;

    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    public List<DishDTO> findAll() {
        return dishRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }


    public DishDTO createDish(CreateDishRequest request) {
        Dish dish = new Dish();
        dish.setName(request.getName());
        dish.setDescription(request.getDescription());
        dish.setCategory(request.getCategory());
        dish.setPrice(request.getPrice());
        dish.setQuantity(request.getQuantity());
        dish.setCreatedAt(LocalDateTime.now());
        dish.setCreatedBy(1L);

        dish = dishRepository.save(dish);
        return convertToDTO(dish);
    }

    public DishDTO updateDish(Long dishId, UpdateDishRequest request) {
        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new DishNotFoundException(dishId));

        /*if (!dish.getCreatedBy().equals(userId)) {
            throw new UnauthorizedEditException("You can only edit your own dishes");
        }*/

        dish.setName(request.getName());
        dish.setDescription(request.getDescription());
        dish.setCategory(request.getCategory());
        dish.setPrice(request.getPrice());
        dish.setQuantity(request.getQuantity());

        dish = dishRepository.save(dish);
        return convertToDTO(dish);
    }

    public void deleteDish(Long dishId) {
        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new DishNotFoundException(dishId));

        /*if (!dish.getCreatedBy().equals(userId)) {
            throw new UnauthorizedEditException("You can only edit your own dishes");
        }*/

        dishRepository.deleteById(dishId);
    }

    public Dish getDishById(Long dishId) {
        return dishRepository.findById(dishId)
                .orElseThrow(() -> new DishNotFoundException(dishId));
    }

    private DishDTO convertToDTO(Dish dish) {
        DishDTO dto = new DishDTO();
        dto.setId(dish.getId());
        dto.setName(dish.getName());
        dto.setDescription(dish.getDescription());
        dto.setCategory(dish.getCategory());
        dto.setPrice(dish.getPrice());
        dto.setQuantity(dish.getQuantity());
        dto.setStopped(dish.getQuantity() == 0);
        dto.setCreatedAt(dish.getCreatedAt());
        dto.setCreatedBy(dish.getCreatedBy());
        return dto;
    }
}
