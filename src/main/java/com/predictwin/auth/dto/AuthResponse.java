package com.predictwin.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Generic auth response wrapper")
public class AuthResponse {
    @Schema(description = "Success status")
    private boolean success;

    @Schema(description = "Message")
    private String message;

    @Schema(description = "Response payload")
    private Object data;
}
