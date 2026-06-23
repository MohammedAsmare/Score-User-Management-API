package com.predictwin.match.controller;

import com.predictwin.match.dto.MatchRequest;
import com.predictwin.match.entity.Match;
import com.predictwin.match.repository.MatchRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
@Tag(name = "Matches", description = "Match management endpoints")
public class MatchController {
    private final MatchRepository matchRepository;

    public MatchController(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @Operation(summary = "Create a match")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody MatchRequest req) {
        Match m = new Match();
        m.setHomeTeam(req.getHomeTeam());
        m.setAwayTeam(req.getAwayTeam());
        m.setLeague(req.getLeague());
        m.setKickoffTime(req.getKickoffTime());
        Match saved = matchRepository.save(m);
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
        List<Match> all = matchRepository.findAll();
        return ResponseEntity.ok(new java.util.HashMap<>() {
            {
                put("success", true);
                put("data", all);
            }
        });
    }

    @Operation(summary = "Update a match")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody MatchRequest req) {
        return matchRepository.findById(id).map(m -> {
            m.setHomeTeam(req.getHomeTeam());
            m.setAwayTeam(req.getAwayTeam());
            m.setLeague(req.getLeague());
            m.setKickoffTime(req.getKickoffTime());
            matchRepository.save(m);
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
        }).orElse(ResponseEntity.notFound().build());
    }
}
