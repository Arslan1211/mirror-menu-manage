package com.example.demo.repository;

import com.example.demo.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DishRepository extends JpaRepository<Dish, Long> {

    List<Dish> findAll();

    Optional<Dish> findByIdAndCreatedBy(Long id, Long userId);
}
