package com.soccerusermanagement.match.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Schema(description = "Create or update match request")
public class MatchRequest {
    @Schema(description = "Home team name", example = "Arsenal")
    @NotBlank
    private String homeTeam;

    @Schema(description = "Away team name", example = "Chelsea")
    @NotBlank
    private String awayTeam;

    @Schema(description = "League name", example = "Premier League")
    private String league;

    @Schema(description = "Kickoff time in ISO-8601", example = "2026-07-01T18:00:00Z")
    @NotNull
    private OffsetDateTime kickoffTime;
}
