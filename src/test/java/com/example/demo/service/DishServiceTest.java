package com.example.demo.service;

import com.example.demo.dto.CreateDishRequest;
import com.example.demo.dto.DishDTO;
import com.example.demo.dto.UpdateDishRequest;
import com.example.demo.entity.Dish;
import com.example.demo.entity.User;
import com.example.demo.exeption.DishNotFoundException;
import com.example.demo.mapper.DishMapper;
import com.example.demo.repository.DishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DishServiceTest {

    @Mock private DishRepository dishRepository;
    @Mock private DishMapper dishMapper;
    @InjectMocks private DishService dishService;

    private Dish testDish;
    private User testUser;
    private final Long EXISTING_DISH_ID = 1L;
    private final Long NON_EXISTENT_DISH_ID = 99L;

    @BeforeEach
    void setUp() {
        testUser = createTestUser(1L, "testuser");
        testDish = createTestDish(EXISTING_DISH_ID, "Test Dish", "Test Description",
                10, 5, testUser.getId());
    }

    // Вспомогательные методы для создания тестовых данных
    private User createTestUser(Long id, String username) {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        return user;
    }

    private Dish createTestDish(Long id, String name, String description,
                                int price, int quantity, Long createdBy) {
        Dish dish = new Dish();
        dish.setId(id);
        dish.setName(name);
        dish.setDescription(description);
        dish.setPrice(price);
        dish.setQuantity(quantity);
        dish.setCreatedBy(createdBy);
        dish.setCreatedAt(LocalDateTime.now());
        dish.setStopped(false);
        return dish;
    }

    private DishDTO createDishDTO(Dish dish) {
        DishDTO dto = new DishDTO();
        dto.setId(dish.getId());
        dto.setName(dish.getName());
        dto.setDescription(dish.getDescription());
        dto.setPrice(dish.getPrice());
        dto.setQuantity(dish.getQuantity());
        dto.setCreatedBy(dish.getCreatedBy());
        dto.setCreatedAt(dish.getCreatedAt());
        dto.setStopped(dish.isStopped());
        return dto;
    }

    @Test
    @DisplayName("Получение всех блюд - успешный сценарий")
    void getAllDishes_ShouldReturnListOfDishDTOs() {
        // Arrange
        Dish secondDish = createTestDish(2L, "Second Dish", null, 15, 0, testUser.getId());
        List<Dish> dishes = List.of(testDish, secondDish);

        when(dishRepository.findAll()).thenReturn(dishes);
        when(dishMapper.toDto(testDish)).thenReturn(createDishDTO(testDish));
        when(dishMapper.toDto(secondDish)).thenReturn(createDishDTO(secondDish));

        // Act
        List<DishDTO> result = dishService.getAllDishes();

        // Assert
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(2, result.size()),
                () -> assertEquals(testDish.getId(), result.get(0).getId()),
                () -> assertEquals(secondDish.getId(), result.get(1).getId())
        );
        verify(dishRepository).findAll();
        verify(dishMapper, times(2)).toDto(any());
    }

    @Test
    @DisplayName("Создание блюда - успешный сценарий")
    void createDish_ShouldSaveDishWithCreatorAndReturnDTO() {
        // Arrange
        CreateDishRequest request = new CreateDishRequest();
        request.setName("New Dish");
        request.setDescription("New Description");
        request.setPrice(12);
        request.setQuantity(10);

        Dish newDish = createTestDish(null, request.getName(), request.getDescription(),
                request.getPrice(), request.getQuantity(), null);
        Dish savedDish = createTestDish(EXISTING_DISH_ID, request.getName(), request.getDescription(),
                request.getPrice(), request.getQuantity(), testUser.getId());
        DishDTO expectedDTO = createDishDTO(savedDish);

        when(dishMapper.toEntity(request)).thenReturn(newDish);
        when(dishRepository.save(any())).thenReturn(savedDish);
        when(dishMapper.toDto(savedDish)).thenReturn(expectedDTO);

        // Act
        DishDTO result = dishService.createDish(request, testUser);

        // Assert
        assertDishDTOEquals(expectedDTO, result);
        verify(dishMapper).toEntity(request);
        verify(dishRepository).save(any());
        verify(dishMapper).toDto(savedDish);
    }

    // Вспомогательный метод для проверки DTO
    private void assertDishDTOEquals(DishDTO expected, DishDTO actual) {
        assertAll(
                () -> assertEquals(expected.getId(), actual.getId()),
                () -> assertEquals(expected.getName(), actual.getName()),
                () -> assertEquals(expected.getDescription(), actual.getDescription()),
                () -> assertEquals(expected.getPrice(), actual.getPrice()),
                () -> assertEquals(expected.getQuantity(), actual.getQuantity()),
                () -> assertEquals(expected.getCreatedBy(), actual.getCreatedBy()),
                () -> assertNotNull(actual.getCreatedAt()),
                () -> assertEquals(expected.isStopped(), actual.isStopped())
        );
    }

    @Test
    @DisplayName("Обновление блюда - успешный сценарий")
    void updateDish_WhenDishExists_ShouldUpdateAndReturnDTO() {
        // Arrange
        UpdateDishRequest request = new UpdateDishRequest();
        request.setName("Updated Dish");
        request.setDescription("Updated Description");
        request.setPrice(15);
        request.setQuantity(10);

        Dish updatedDish = createTestDish(EXISTING_DISH_ID, request.getName(), request.getDescription(),
                request.getPrice(), request.getQuantity(), testUser.getId());
        DishDTO expectedDTO = createDishDTO(updatedDish);

        when(dishRepository.findByIdAndCreatedBy(EXISTING_DISH_ID, testUser.getId()))
                .thenReturn(Optional.of(testDish));
        when(dishRepository.save(testDish)).thenReturn(updatedDish);
        when(dishMapper.toDto(updatedDish)).thenReturn(expectedDTO);

        // Act
        DishDTO result = dishService.updateDish(EXISTING_DISH_ID, request, testUser);

        // Assert
        assertDishDTOEquals(expectedDTO, result);
        verify(dishRepository).findByIdAndCreatedBy(EXISTING_DISH_ID, testUser.getId());
        verify(dishMapper).updateEntityFromRequest(request, testDish);
        verify(dishRepository).save(testDish);
        verify(dishMapper).toDto(updatedDish);
    }


    @Test
    @DisplayName("Обновление блюда - блюдо не найдено")
    void updateDish_WhenDishNotFound_ShouldThrowException() {
        // Arrange
        UpdateDishRequest request = new UpdateDishRequest();
        request.setName("Updated Dish");
        request.setDescription("Updated Description");
        request.setPrice(15);
        request.setQuantity(10);

        when(dishRepository.findByIdAndCreatedBy(NON_EXISTENT_DISH_ID, testUser.getId()))
                .thenReturn(Optional.empty());

        // Act & Assert
        DishNotFoundException exception = assertThrows(DishNotFoundException.class,
                () -> dishService.updateDish(NON_EXISTENT_DISH_ID, request, testUser));

        assertEquals(String.format("Dish not found with %d or you don't have permission", NON_EXISTENT_DISH_ID), exception.getMessage());

        verify(dishRepository, times(1)).findByIdAndCreatedBy(NON_EXISTENT_DISH_ID, testUser.getId());
        verify(dishMapper, never()).updateEntityFromRequest(any(), any());
        verify(dishRepository, never()).save(any());
        verify(dishMapper, never()).toDto(any());
    }

    @Test
    @DisplayName("Удаление блюда - успешный сценарий")
    void deleteDish_WhenDishExists_ShouldDeleteDish() {
        // Arrange
        when(dishRepository.findByIdAndCreatedBy(EXISTING_DISH_ID, testUser.getId()))
                .thenReturn(Optional.of(testDish));

        // Act
        dishService.deleteDish(EXISTING_DISH_ID, testUser);

        // Assert
        verify(dishRepository, times(1)).findByIdAndCreatedBy(EXISTING_DISH_ID, testUser.getId());
        verify(dishRepository, times(1)).delete(testDish);
    }

    @Test
    @DisplayName("Получение блюда по ID - успешный сценарий")
    void getDishById_WhenDishExists_ShouldReturnDishDTO() {
        // Arrange
        DishDTO expectedDTO = new DishDTO();
        expectedDTO.setId(testDish.getId());
        expectedDTO.setName(testDish.getName());
        expectedDTO.setDescription(testDish.getDescription());
        expectedDTO.setPrice(testDish.getPrice());
        expectedDTO.setQuantity(testDish.getQuantity());
        expectedDTO.setCreatedBy(testDish.getCreatedBy());
        expectedDTO.setCreatedAt(testDish.getCreatedAt());
        expectedDTO.setStopped(testDish.isStopped());

        when(dishRepository.findById(EXISTING_DISH_ID)).thenReturn(Optional.of(testDish));
        when(dishMapper.toDto(testDish)).thenReturn(expectedDTO);

        // Act
        DishDTO result = dishService.getDishById(EXISTING_DISH_ID);

        // Assert
        assertAll(
                () -> assertNotNull(result, "DTO не должно быть null"),
                () -> assertEquals(expectedDTO.getId(), result.getId(), "ID не совпадает"),
                () -> assertEquals(expectedDTO.getName(), result.getName(), "Название не совпадает"),
                () -> assertEquals(expectedDTO.getDescription(), result.getDescription(), "Описание не совпадает"),
                () -> assertEquals(expectedDTO.getPrice(), result.getPrice(), "Цена не совпадает"),
                () -> assertEquals(expectedDTO.getQuantity(), result.getQuantity(), "Количество не совпадает"),
                () -> assertEquals(expectedDTO.getCreatedBy(), result.getCreatedBy(), "Создатель не совпадает"),
                () -> assertEquals(expectedDTO.getCreatedAt(), result.getCreatedAt(), "Дата создания не совпадает"),
                () -> assertEquals(expectedDTO.isStopped(), result.isStopped(), "Статус stopped не совпадает")
        );

        verify(dishRepository, times(1)).findById(EXISTING_DISH_ID);
        verify(dishMapper, times(1)).toDto(testDish);
    }

    @Test
    @DisplayName("Получение блюда по ID - блюдо не найдено")
    void getDishById_WhenDishNotFound_ShouldThrowException() {
        // Arrange
        when(dishRepository.findById(NON_EXISTENT_DISH_ID)).thenReturn(Optional.empty());

        // Act & Assert
        DishNotFoundException exception = assertThrows(DishNotFoundException.class,
                () -> dishService.getDishById(NON_EXISTENT_DISH_ID));

        assertEquals(String.format("Dish not found with %d or you don't have permission", NON_EXISTENT_DISH_ID), exception.getMessage());
        verify(dishRepository, times(1)).findById(NON_EXISTENT_DISH_ID);
    }

    @Test
    @DisplayName("Удаление блюда - блюдо не найдено")
    void deleteDish_WhenDishNotFound_ShouldThrowException() {
        // Arrange
        when(dishRepository.findByIdAndCreatedBy(NON_EXISTENT_DISH_ID, testUser.getId()))
                .thenReturn(Optional.empty());

        // Act & Assert
        DishNotFoundException exception = assertThrows(DishNotFoundException.class,
                () -> dishService.deleteDish(NON_EXISTENT_DISH_ID, testUser));

        assertEquals(String.format("Dish not found with %d or you don't have permission", NON_EXISTENT_DISH_ID), exception.getMessage());
        verify(dishRepository, times(1)).findByIdAndCreatedBy(NON_EXISTENT_DISH_ID, testUser.getId());
        verify(dishRepository, never()).delete(any());
    }
}