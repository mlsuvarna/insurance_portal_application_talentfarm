package com.insurance.insuranceportal.controller;

import com.insurance.insuranceportal.dto.LoginRequest;
import com.insurance.insuranceportal.dto.LoginResponse;
import com.insurance.insuranceportal.dto.RegisterRequest;
import com.insurance.insuranceportal.dto.RegisterResponse;
import com.insurance.insuranceportal.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerUser(
            @Valid @RequestBody RegisterRequest request
    ) {

        RegisterResponse response =
                userService.registerUser(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(
            @Valid @RequestBody LoginRequest request
    ) {

        LoginResponse response =
                userService.loginUser(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}