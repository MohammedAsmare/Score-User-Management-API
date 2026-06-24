package com.soccerusermanagement.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Register request payload")
public class RegisterRequest {
    @Schema(description = "Desired username", example = "moh")
    @NotBlank
    @Size(max = 50)
    private String username;

    @Schema(description = "User email", example = "moh@gmail.com")
    @Email
    @NotBlank
    private String email;

    @Schema(description = "Password", example = "123456")
    @NotBlank
    @Size(min = 6)
    private String password;

    public RegisterRequest() {
    }

    public RegisterRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
