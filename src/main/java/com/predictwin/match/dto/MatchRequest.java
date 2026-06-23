package com.predictwin.match.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Schema(description = "Create or update match request")
public class MatchRequest {
    @Schema(description = "Home team name", example = "Arsenal")
    private String homeTeam;

    @Schema(description = "Away team name", example = "Chelsea")
    private String awayTeam;

    @Schema(description = "League name", example = "Premier League")
    private String league;

    @Schema(description = "Kickoff time in ISO-8601", example = "2026-07-01T18:00:00Z")
    private OffsetDateTime kickoffTime;
}
