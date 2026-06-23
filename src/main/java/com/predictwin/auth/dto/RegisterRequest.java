package com.predictwin.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Register request payload")
public class RegisterRequest {
    @Schema(description = "Desired username", example = "moh")
    private String username;

    @Schema(description = "User email", example = "moh@gmail.com")
    private String email;

    @Schema(description = "Password", example = "123456")
    private String password;
}
