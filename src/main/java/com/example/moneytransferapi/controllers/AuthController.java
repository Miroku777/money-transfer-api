package com.example.moneytransferapi.controllers;

import com.example.moneytransferapi.dto.AuthResponse;
import com.example.moneytransferapi.services.UserService;
import com.example.moneytransferapi.dto.AuthRequest;
import com.example.moneytransferapi.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Operation(summary = "Get JWT token",
            description = "Use email or phone. " +
                    "Login and Passwords: " +
                    "user@mail.ru or 79207865432 + User_password, " +
                    "alice@example.com or 79123456789 + Alice_password, " +
                    "bob@example.com or 79876543210 + Bob_password")
    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody AuthRequest request) {
        Long userId = userService.authenticate(request.getLogin(), request.getPassword());
        String token = jwtUtil.generateToken(userId);
        AuthResponse response = new AuthResponse();
        response.setToken(token);
        return response;
    }
}