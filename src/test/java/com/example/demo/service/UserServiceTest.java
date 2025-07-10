package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.exeption.UsernameExistsException;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setPassword("password");
    }

    @Test
    @DisplayName("Загрузка пользователя по username - успешный сценарий")
    void loadUserByUsername_WhenUserExists_ShouldReturnUser() {
        // Arrange
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        // Act
        UserDetails result = userService.loadUserByUsername("testuser");

        // Assert
        assertEquals(testUser.getUsername(), result.getUsername());
        verify(userRepository).findByUsername("testuser");
    }

    @Test
    @DisplayName("Загрузка пользователя по username - пользователь не найден")
    void loadUserByUsername_WhenUserNotExists_ShouldThrowException() {
        // Arrange
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        // Act & Assert
        com.example.demo.exeption.UsernameNotFoundException exception =
                assertThrows(com.example.demo.exeption.UsernameNotFoundException.class,
                        () -> userService.loadUserByUsername("unknown"));

        assertEquals("Username unknown not found", exception.getMessage());
        verify(userRepository).findByUsername("unknown");
    }

    @Test
    @DisplayName("Регистрация нового пользователя - успешный сценарий")
    void register_WhenUsernameAvailable_ShouldSaveAndReturnUser() {
        // Arrange
        String encodedPassword = "encodedPassword";
        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(passwordEncoder.encode("password")).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(2L); // Simulate saved user
            return user;
        });

        User newUser = new User();
        newUser.setUsername("newuser");
        newUser.setPassword("password");

        // Act
        User result = userService.register(newUser);

        // Assert
        assertAll(
                () -> assertNotNull(result.getId(), "ID должен быть установлен"),
                () -> assertEquals("newuser", result.getUsername(), "Username не совпадает"),
                () -> assertEquals(encodedPassword, result.getPassword(), "Пароль должен быть зашифрован")
        );
        verify(userRepository).existsByUsername("newuser");
        verify(passwordEncoder).encode("password");
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Регистрация нового пользователя - username уже занят")
    void register_WhenUsernameTaken_ShouldThrowException() {
        // Arrange
        when(userRepository.existsByUsername("existing")).thenReturn(true);

        User newUser = new User();
        newUser.setUsername("existing");
        newUser.setPassword("password");

        // Act & Assert
        UsernameExistsException exception = assertThrows(UsernameExistsException.class,
                () -> userService.register(newUser));

        assertEquals("Username existing already exists", exception.getMessage());
        verify(userRepository).existsByUsername("existing");
        verify(passwordEncoder, never()).encode(any());
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Регистрация нового пользователя - проверка шифрования пароля")
    void register_ShouldEncodePasswordBeforeSaving() {
        // Arrange
        String rawPassword = "password";
        String encodedPassword = "encodedPassword";

        User newUser = new User();
        newUser.setUsername("newuser");
        newUser.setPassword(rawPassword);

        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        User result = userService.register(newUser);

        // Assert
        assertEquals(encodedPassword, result.getPassword(), "Пароль должен быть зашифрован");
        verify(passwordEncoder).encode(rawPassword);
    }
}