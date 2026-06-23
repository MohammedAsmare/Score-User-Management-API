package com.predictwin.prediction.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Prediction request payload")
public class PredictionRequest {
    @Schema(description = "Match id to predict", example = "12")
    private Long matchId;

    @Schema(description = "Predicted home score", example = "2")
    private Integer homeScore;

    @Schema(description = "Predicted away score", example = "1")
    private Integer awayScore;
}
