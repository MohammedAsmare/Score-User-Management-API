package com.soccerusermanagement.match.controller;

import com.soccerusermanagement.match.dto.MatchRequest;
import com.soccerusermanagement.match.entity.Match;
import com.soccerusermanagement.match.repository.MatchRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
@Tag(name = "Matches", description = "Match management endpoints")
public class MatchController {
    private final MatchRepository matchRepository;
    private static final Logger log = LoggerFactory.getLogger(MatchController.class);

    public MatchController(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @Operation(summary = "Create a match")
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody MatchRequest req) {
        log.info("Create match request received homeTeam={} awayTeam={} league={} kickoffTime={}", req.getHomeTeam(),
                req.getAwayTeam(), req.getLeague(), req.getKickoffTime());
        Match m = new Match();
        m.setHomeTeam(req.getHomeTeam());
        m.setAwayTeam(req.getAwayTeam());
        m.setLeague(req.getLeague());
        m.setKickoffTime(req.getKickoffTime());
        Match saved = matchRepository.save(m);
        log.info("Match created id={} homeTeam={} awayTeam={}", saved.getId(), saved.getHomeTeam(),
                saved.getAwayTeam());
        return ResponseEntity.ok(new java.util.HashMap<>() {
            {
                put("success", true);
                put("message", "Match created");
                put("data", new java.util.HashMap<>() {
                    {
                        put("id", saved.getId());
                        put("homeTeam", saved.getHomeTeam());
                        put("awayTeam", saved.getAwayTeam());
                        put("league", saved.getLeague());
                    }
                });
            }
        });
    }

    @Operation(summary = "List matches")
    @GetMapping
    public ResponseEntity<?> list() {
        log.debug("GET /api/matches - list request");
        List<Match> all = matchRepository.findAll();
        log.info("Listed matches count={}", all.size());
        return ResponseEntity.ok(new java.util.HashMap<>() {
            {
                put("success", true);
                put("data", all);
            }
        });
    }

    @Operation(summary = "Update a match")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody MatchRequest req) {
        log.info("Update match request received id={} homeTeam={} awayTeam={} league={} kickoffTime={}", id,
                req.getHomeTeam(), req.getAwayTeam(), req.getLeague(), req.getKickoffTime());
        return matchRepository.findById(id).map(m -> {
            m.setHomeTeam(req.getHomeTeam());
            m.setAwayTeam(req.getAwayTeam());
            m.setLeague(req.getLeague());
            m.setKickoffTime(req.getKickoffTime());
            matchRepository.save(m);
            log.info("Match updated id={} homeTeam={} awayTeam={}", m.getId(), m.getHomeTeam(), m.getAwayTeam());
            return ResponseEntity.ok(new java.util.HashMap<>() {
                {
                    put("success", true);
                    put("message", "Match updated");
                    put("data", new java.util.HashMap<>() {
                        {
                            put("id", m.getId());
                        }
                    });
                }
            });
        }).orElseGet(() -> {
            log.warn("Update match failed because id={} was not found", id);
            return ResponseEntity.ok(new java.util.HashMap<>() {
                {
                    put("code", 404);
                    put("success", false);
                    put("message", "Match not found");
                    put("data", null);
                }
            });
        });
    }
}
