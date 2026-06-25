package com.soccerusermanagement.token.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Token response")
public class TokenResponse {
    @Schema(description = "Response code", example = "200")
    private int code;

    @Schema(description = "Success status")
    private boolean success;

    @Schema(description = "Message")
    private String message;

    @Schema(description = "Generated token")
    private String token;

    @Schema(description = "Token expiration timestamp", example = "2025-06-25T12:00:00.000+00:00")
    private String expireOn;

    public TokenResponse() {
    }

    public TokenResponse(int code, boolean success, String message, String token, String expireOn) {
        this.code = code;
        this.success = success;
        this.message = message;
        this.token = token;
        this.expireOn = expireOn;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExpireOn() {
        return expireOn;
    }

    public void setExpireOn(String expireOn) {
        this.expireOn = expireOn;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}