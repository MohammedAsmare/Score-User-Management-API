package com.predictwin.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Login request payload")
public class LoginRequest {
    @Schema(description = "User email", example = "moh@gmail.com")
    private String email;

    @Schema(description = "Password", example = "123456")
    private String password;
}
