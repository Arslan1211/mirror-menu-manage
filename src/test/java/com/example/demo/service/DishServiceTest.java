/*
package com.example.demo.service;

import com.example.demo.entity.Dish;
import com.example.demo.entity.User;
import com.example.demo.exeption.DishNotFoundException;
import com.example.demo.repository.DishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DishServiceTest {

    @Mock
    private DishRepository dishRepository;

    @InjectMocks
    private DishService dishService;

    private Dish testDish;
    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);

        testDish = new Dish();
        testDish.setId(1L);
        testDish.setName("Test Dish");
        testDish.setDescription("Test Description");
        testDish.setPrice(10);
        testDish.setQuantity(5);
        testDish.setCreatedBy(testUser.getId());
    }

    @Test
    void getAllDishes_ShouldReturnAllDishes() {
        // Arrange
        Dish dish2 = new Dish();
        dish2.setId(2L);
        List<Dish> expectedDishes = Arrays.asList(testDish, dish2);

        when(dishRepository.findAll()).thenReturn(expectedDishes);

        // Act
        List<Dish> actualDishes = dishService.getAllDishes();

        // Assert
        assertEquals(expectedDishes.size(), actualDishes.size());
        assertEquals(expectedDishes, actualDishes);
        verify(dishRepository, times(1)).findAll();
    }

    @Test
    void createDish_ShouldSaveAndReturnDish() {
        // Arrange
        when(dishRepository.save(testDish)).thenReturn(testDish);

        // Act
        Dish createdDish = dishService.createDish(testDish, testUser);

        // Assert
        assertNotNull(createdDish);
        assertEquals(testUser.getId(), createdDish.getCreatedBy());
        verify(dishRepository, times(1)).save(testDish);
    }

    @Test
    void updateDish_WhenDishExistsAndUserIsCreator_ShouldUpdateAndReturnDish() {
        // Arrange
        Dish updatedDetails = new Dish();
        updatedDetails.setName("Updated Name");
        updatedDetails.setDescription("Updated Description");
        updatedDetails.setPrice(15);
        updatedDetails.setQuantity(10);

        when(dishRepository.findByIdAndCreatedBy(testDish.getId(), testUser.getId()))
                .thenReturn(Optional.of(testDish));
        when(dishRepository.save(testDish)).thenReturn(testDish);

        // Act
        Dish updatedDish = dishService.updateDish(testDish.getId(), updatedDetails, testUser);

        // Assert
        assertEquals(updatedDetails.getName(), updatedDish.getName());
        assertEquals(updatedDetails.getDescription(), updatedDish.getDescription());
        assertEquals(updatedDetails.getPrice(), updatedDish.getPrice());
        assertEquals(updatedDetails.getQuantity(), updatedDish.getQuantity());
        assertEquals(testUser.getId(), updatedDish.getCreatedBy());
        verify(dishRepository, times(1)).findByIdAndCreatedBy(testDish.getId(), testUser.getId());
        verify(dishRepository, times(1)).save(testDish);
    }

    @Test
    void updateDish_WhenDishNotFound_ShouldThrowException() {
        // Arrange
        Long nonExistentId = 99L;
        when(dishRepository.findByIdAndCreatedBy(nonExistentId, testUser.getId()))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DishNotFoundException.class, () -> {
            dishService.updateDish(nonExistentId, testDish, testUser);
        });
        verify(dishRepository, times(1)).findByIdAndCreatedBy(nonExistentId, testUser.getId());
        verify(dishRepository, never()).save(any());
    }

    @Test
    void deleteDish_WhenDishExistsAndUserIsCreator_ShouldDeleteDish() {
        // Arrange
        when(dishRepository.findByIdAndCreatedBy(testDish.getId(), testUser.getId()))
                .thenReturn(Optional.of(testDish));

        // Act
        dishService.deleteDish(testDish.getId(), testUser);

        // Assert
        verify(dishRepository, times(1)).findByIdAndCreatedBy(testDish.getId(), testUser.getId());
        verify(dishRepository, times(1)).delete(testDish);
    }

    @Test
    void deleteDish_WhenDishNotFound_ShouldThrowException() {
        // Arrange
        Long nonExistentId = 99L;
        when(dishRepository.findByIdAndCreatedBy(nonExistentId, testUser.getId()))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DishNotFoundException.class, () -> {
            dishService.deleteDish(nonExistentId, testUser);
        });
        verify(dishRepository, times(1)).findByIdAndCreatedBy(nonExistentId, testUser.getId());
        verify(dishRepository, never()).delete(any());
    }

    @Test
    void getDishById_WhenDishExists_ShouldReturnDish() {
        // Arrange
        when(dishRepository.findById(testDish.getId())).thenReturn(Optional.of(testDish));

        // Act
        Dish foundDish = dishService.getDishById(testDish.getId());

        // Assert
        assertNotNull(foundDish);
        assertEquals(testDish.getId(), foundDish.getId());
        verify(dishRepository, times(1)).findById(testDish.getId());
    }

    @Test
    void getDishById_WhenDishNotFound_ShouldThrowException() {
        // Arrange
        Long nonExistentId = 99L;
        when(dishRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DishNotFoundException.class, () -> {
            dishService.getDishById(nonExistentId);
        });
        verify(dishRepository, times(1)).findById(nonExistentId);
    }
}*/
