package com.predictwin.auth.controller;

import com.predictwin.auth.dto.AuthResponse;
import com.predictwin.auth.dto.LoginRequest;
import com.predictwin.auth.dto.RegisterRequest;
import com.predictwin.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "Authentication endpoints")
public class AuthController {
    private final AuthService authService;
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest req) {
        log.info("Register request received for email={} username={}", req.getEmail(), req.getUsername());
        AuthResponse response = authService.register(req);
        if (!response.isSuccess()) {
            log.warn("Register request rejected for email={} username={} reason={}", req.getEmail(), req.getUsername(),
                    response.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
        log.info("Register request completed for email={} username={}", req.getEmail(), req.getUsername());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Login with email and password")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest req) {
        log.info("Login request received for email={}", req.getEmail());
        AuthResponse response = authService.login(req);
        if (!response.isSuccess()) {
            log.warn("Login request rejected for email={} reason={}", req.getEmail(), response.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        log.info("Login request completed for email={}", req.getEmail());
        return ResponseEntity.ok(response);
    }
}
