package com.example.demo.service;

import com.example.demo.config.JwtUtils;
import com.example.demo.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_Success() {
        // Arrange
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(user);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtUtils.generateToken(user)).thenReturn("test-token");

        // Act
        String token = authService.login(user.getUsername(), user.getPassword());

        // Assert
        assertEquals("test-token", token);
        verify(authenticationManager).authenticate(any());
        verify(jwtUtils).generateToken(user);
    }

    @Test
    void register_Success() {
        // Arrange
        User newUser = new User();
        newUser.setUsername("newuser");
        newUser.setPassword("password");
        when(userService.register(newUser)).thenReturn(newUser);

        // Act
        User registeredUser = authService.register(newUser);

        // Assert
        assertNotNull(registeredUser);
        assertEquals("newuser", registeredUser.getUsername());
        verify(userService).register(newUser);
    }
}