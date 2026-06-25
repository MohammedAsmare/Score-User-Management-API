package com.soccerusermanagement.token.controller;

import com.soccerusermanagement.token.dto.TokenValidationResponse;
import com.soccerusermanagement.token.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/token")
@Tag(name = "Token", description = "Token generation and validation endpoints")
public class TokenValidateController {
    private final TokenService tokenService;
    private static final Logger log = LoggerFactory.getLogger(TokenValidateController.class);

    public TokenValidateController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Operation(summary = "Validate a JWT token")
    @GetMapping("/validate")
    public ResponseEntity<TokenValidationResponse> validateToken(
            @Parameter(description = "JWT token to validate", required = true) @RequestParam(value = "token", required = true) String token) {
        log.info("GET /api/token/validate - request received");

        TokenValidationResponse response = tokenService.validateToken(token);
        if (response.isSuccess()) {
            log.info("Token validation completed successfully");
            return ResponseEntity.ok(response);
        } else {
            log.warn("Token validation failed reason={}", response.getMessage());
            return ResponseEntity.status(HttpStatus.valueOf(response.getCode())).body(response);
        }
    }
}
