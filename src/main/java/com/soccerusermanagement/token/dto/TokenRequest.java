package com.soccerusermanagement.token.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Token generation request")
public class TokenRequest {
    @Schema(description = "Username", example = "john_doe")
    @NotBlank(message = "Username is required")
    private String username;

    @Schema(description = "Password", example = "password123")
    @NotBlank(message = "Password is required")
    private String password;

    public TokenRequest() {
    }

    public TokenRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}