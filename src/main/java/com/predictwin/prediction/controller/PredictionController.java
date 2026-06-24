package com.predictwin.prediction.controller;

import com.predictwin.prediction.dto.PredictionRequest;
import com.predictwin.prediction.entity.Prediction;
import com.predictwin.prediction.repository.PredictionRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/predictions")
@Tag(name = "Predictions", description = "Prediction endpoints")
@SecurityRequirement(name = "bearerAuth")
public class PredictionController {
    private static final Logger log = LoggerFactory.getLogger(PredictionController.class);

    private final PredictionRepository predictionRepository;

    public PredictionController(PredictionRepository predictionRepository) {
        this.predictionRepository = predictionRepository;
    }

    @Operation(summary = "Create a prediction")
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody PredictionRequest req, Authentication auth) {
        Long userId = (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName()))
                ? Long.parseLong(auth.getName())
                : null;
        log.info("Create prediction request received userId={} matchId={} homeScore={} awayScore={}", userId,
                req.getMatchId(), req.getHomeScore(), req.getAwayScore());
        Prediction p = new Prediction();
        p.setUserId(userId);
        p.setMatchId(req.getMatchId());
        p.setPredictedHomeScore(req.getHomeScore());
        p.setPredictedAwayScore(req.getAwayScore());
        Prediction saved = predictionRepository.save(p);
        log.info("Prediction saved id={} userId={} matchId={}", saved.getId(), saved.getUserId(),
                saved.getMatchId());
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
        Long userId = (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName()))
                ? Long.parseLong(auth.getName())
                : null;
        log.debug("List predictions request received userId={}", userId);
        List<Prediction> list = predictionRepository.findByUserId(userId);
        log.info("Listed predictions userId={} count={}", userId, list.size());
        return ResponseEntity.ok(new java.util.HashMap<>() {
            {
                put("success", true);
                put("data", list);
            }
        });
    }

    @Operation(summary = "Update a prediction owned by the authenticated user")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody PredictionRequest req,
            Authentication auth) {
        Long userId = (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName()))
                ? Long.parseLong(auth.getName())
                : null;
        log.info("Update prediction request received predictionId={} userId={} homeScore={} awayScore={}", id, userId,
                req.getHomeScore(), req.getAwayScore());
        return predictionRepository.findById(id).map(p -> {
            if (!p.getUserId().equals(userId)) {
                log.warn("Update prediction forbidden predictionId={} ownerUserId={} requestUserId={}", id,
                        p.getUserId(), userId);
                return ResponseEntity.status(403).build();
            }
            p.setPredictedHomeScore(req.getHomeScore());
            p.setPredictedAwayScore(req.getAwayScore());
            predictionRepository.save(p);
            log.info("Prediction updated id={} userId={} matchId={}", p.getId(), p.getUserId(), p.getMatchId());
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
        }).orElseGet(() -> {
            log.warn("Update prediction failed because id={} was not found", id);
            return ResponseEntity.notFound().build();
        });
    }
}
