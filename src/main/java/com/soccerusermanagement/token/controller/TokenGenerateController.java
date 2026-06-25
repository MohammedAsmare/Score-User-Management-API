package com.soccerusermanagement.token.controller;

import com.soccerusermanagement.token.dto.TokenRequest;
import com.soccerusermanagement.token.dto.TokenResponse;
import com.soccerusermanagement.token.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/token")
@Tag(name = "Token", description = "Token generation and validation endpoints")
public class TokenGenerateController {
    private final TokenService tokenService;
    private static final Logger log = LoggerFactory.getLogger(TokenGenerateController.class);

    public TokenGenerateController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Operation(summary = "Generate a new JWT token")
    @PostMapping("/generate")
    public ResponseEntity<TokenResponse> generateToken(@Valid @RequestBody TokenRequest request) {
        log.info("POST /api/token/generate - request received username={}", request.getUsername());
        TokenResponse response = tokenService.generateToken(request);
        if (response.isSuccess()) {
            log.info("Token generation completed successfully for username={}", request.getUsername());
            return ResponseEntity.ok(response);
        } else {
            log.warn("Token generation failed for username={} reason={}", request.getUsername(), response.getMessage());
            return ResponseEntity.status(HttpStatus.valueOf(response.getCode())).body(response);
        }
    }
}