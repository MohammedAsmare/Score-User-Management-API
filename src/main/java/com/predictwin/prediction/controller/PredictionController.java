package com.predictwin.prediction.controller;

import com.predictwin.prediction.dto.PredictionRequest;
import com.predictwin.prediction.entity.Prediction;
import com.predictwin.prediction.repository.PredictionRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/predictions")
@Tag(name = "Predictions", description = "Prediction endpoints")
@SecurityRequirement(name = "bearerAuth")
public class PredictionController {
    private final PredictionRepository predictionRepository;

    public PredictionController(PredictionRepository predictionRepository) {
        this.predictionRepository = predictionRepository;
    }

    @Operation(summary = "Create a prediction for the authenticated user")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody PredictionRequest req, Authentication auth) {
        Long userId = Long.parseLong(auth.getName());
        Prediction p = new Prediction();
        p.setUserId(userId);
        p.setMatchId(req.getMatchId());
        p.setPredictedHomeScore(req.getHomeScore());
        p.setPredictedAwayScore(req.getAwayScore());
        Prediction saved = predictionRepository.save(p);
        return ResponseEntity.ok(new java.util.HashMap<>() {
            {
                put("success", true);
                put("message", "Prediction saved");
                put("data", new java.util.HashMap<>() {
                    {
                        put("predictionId", saved.getId());
                        put("matchId", saved.getMatchId());
                        put("homeScore", saved.getPredictedHomeScore());
                        put("awayScore", saved.getPredictedAwayScore());
                    }
                });
            }
        });
    }

    @Operation(summary = "Get predictions for authenticated user")
    @GetMapping("/me")
    public ResponseEntity<?> myPredictions(Authentication auth) {
        Long userId = Long.parseLong(auth.getName());
        List<Prediction> list = predictionRepository.findByUserId(userId);
        return ResponseEntity.ok(new java.util.HashMap<>() {
            {
                put("success", true);
                put("data", list);
            }
        });
    }

    @Operation(summary = "Update a prediction owned by the authenticated user")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody PredictionRequest req, Authentication auth) {
        Long userId = Long.parseLong(auth.getName());
        return predictionRepository.findById(id).map(p -> {
            if (!p.getUserId().equals(userId))
                return ResponseEntity.status(403).build();
            p.setPredictedHomeScore(req.getHomeScore());
            p.setPredictedAwayScore(req.getAwayScore());
            predictionRepository.save(p);
            return ResponseEntity.ok(new java.util.HashMap<>() {
                {
                    put("success", true);
                    put("message", "Prediction updated");
                    put("data", new java.util.HashMap<>() {
                        {
                            put("predictionId", p.getId());
                            put("homeScore", p.getPredictedHomeScore());
                            put("awayScore", p.getPredictedAwayScore());
                        }
                    });
                }
            });
        }).orElse(ResponseEntity.notFound().build());
    }
}
