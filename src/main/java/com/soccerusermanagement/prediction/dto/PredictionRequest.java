package com.soccerusermanagement.prediction.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Prediction request payload")
public class PredictionRequest {
    @Schema(description = "Match id to predict", example = "12")
    @NotNull
    private Long matchId;

    @Schema(description = "Predicted home score", example = "2")
    @NotNull
    @Min(0)
    private Integer homeScore;

    @Schema(description = "Predicted away score", example = "1")
    @NotNull
    @Min(0)
    private Integer awayScore;
}
