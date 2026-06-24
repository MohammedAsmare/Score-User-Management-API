package com.soccerusermanagement.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Generic auth response wrapper")
public class AuthResponse {
    @Schema(description = "Success status")
    private boolean success;

    @Schema(description = "Message")
    private String message;

    @Schema(description = "Response payload")
    private Object data;

    public AuthResponse() {
    }

    public AuthResponse(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
