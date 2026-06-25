package com.soccerusermanagement.token.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Token validation response")
public class TokenValidationResponse {
    @Schema(description = "Response code", example = "200")
    private int code;

    @Schema(description = "Success status")
    private boolean success;

    @Schema(description = "Message")
    private String message;

    public TokenValidationResponse() {
    }

    public TokenValidationResponse(int code, boolean success, String message) {
        this.code = code;
        this.success = success;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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
}