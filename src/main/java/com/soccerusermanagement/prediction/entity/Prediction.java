package com.soccerusermanagement.prediction.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Entity
@Table(name = "predictions", uniqueConstraints = @UniqueConstraint(columnNames = { "user_id", "match_id" }))
@Data
@NoArgsConstructor
public class Prediction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "match_id")
    private Long matchId;

    @Column(name = "predicted_home_score")
    private Integer predictedHomeScore;

    @Column(name = "predicted_away_score")
    private Integer predictedAwayScore;

    @Column(name = "points_awarded")
    private Integer pointsAwarded = 0;

    @Column(name = "created_at")
    private OffsetDateTime createdAt = OffsetDateTime.now();
}
