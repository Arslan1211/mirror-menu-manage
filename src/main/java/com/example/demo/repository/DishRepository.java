package com.example.demo.repository;

import com.example.demo.entity.Dish;
import com.example.demo.service.dto.DishDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DishRepository extends JpaRepository<Dish, Long> {
    List<Dish> findByCreatedBy(Long createdBy);


    DishDTO deleteDishById(Long id);
}
