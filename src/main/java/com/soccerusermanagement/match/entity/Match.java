package com.soccerusermanagement.match.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Entity
@Table(name = "matches")
@Data
@NoArgsConstructor
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "home_team", nullable = false)
    private String homeTeam;

    @Column(name = "away_team", nullable = false)
    private String awayTeam;

    @Column(name = "home_logo")
    private String homeLogo;

    @Column(name = "away_logo")
    private String awayLogo;

    private String league;

    @Column(name = "kickoff_time", nullable = false)
    private OffsetDateTime kickoffTime;

    private String status = "UPCOMING";
}
