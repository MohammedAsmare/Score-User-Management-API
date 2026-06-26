package com.soccerusermanagement.predictwin.controller;

import com.soccerusermanagement.predictwin.dto.GoogleAuthRequest;
import com.soccerusermanagement.predictwin.entity.PredictWinUser;
import com.soccerusermanagement.predictwin.service.PredictWinService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/predictwin")
@Tag(name = "PredictWin", description = "PredictWin authentication and user management endpoints")
public class PredictWinController {
    private final PredictWinService predictWinService;
    private static final Logger log = LoggerFactory.getLogger(PredictWinController.class);

    public PredictWinController(PredictWinService predictWinService) {
        this.predictWinService = predictWinService;
    }

    @Operation(summary = "PredictWin Google authentication - register or login")
    @PostMapping("/auth/google")
    public ResponseEntity<Map<String, Object>> googleAuth(@Valid @RequestBody GoogleAuthRequest req) {
        log.info("POST /api/predictwin/auth/google - request received uid={} name={}", req.getUid(), req.getName());
        try {
            Map<String, Object> response = predictWinService.googleAuth(req);
            log.info("PredictWin GoogleAuth completed uid={} success={}", req.getUid(), response.get("success"));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("PredictWin GoogleAuth error uid={} error={}", req.getUid(), e.getMessage(), e);
            return ResponseEntity.ok(new java.util.HashMap<String, Object>() {
                {
                    put("code", 200);
                    put("success", false);
                    put("message", "Internal server error: " + e.getMessage());
                    put("data", null);
                }
            });
        }
    }

    @Operation(summary = "Get PredictWin user by UID")
    @GetMapping("/users/uid/{uid}")
    public ResponseEntity<Map<String, Object>> getByUid(@PathVariable String uid) {
        log.info("GET /api/predictwin/users/uid/{} - request", uid);
        try {
            return predictWinService.findByUid(uid)
                    .map(user -> {
                        Map<String, Object> response = new java.util.HashMap<>();
                        response.put("code", 200);
                        response.put("success", true);
                        response.put("data", user);
                        return ResponseEntity.ok(response);
                    })
                    .orElseGet(() -> {
                        log.warn("PredictWin user not found for uid={}", uid);
                        Map<String, Object> response = new java.util.HashMap<>();
                        response.put("code", 404);
                        response.put("success", false);
                        response.put("message", "User not found for uid: " + uid);
                        response.put("data", null);
                        return ResponseEntity.ok(response);
                    });
        } catch (Exception e) {
            log.error("Error fetching PredictWin user by uid={} error={}", uid, e.getMessage(), e);
            Map<String, Object> response = new java.util.HashMap<>();
            response.put("code", 200);
            response.put("success", false);
            response.put("message", "Internal server error: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.ok(response);
        }
    }

    @Operation(summary = "Get all PredictWin users")
    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        log.info("GET /api/predictwin/users - list all users");
        try {
            Iterable<PredictWinUser> users = predictWinService.getAllUsers();
            return ResponseEntity.ok(new java.util.HashMap<String, Object>() {
                {
                    put("code", 200);
                    put("success", true);
                    put("data", users);
                }
            });
        } catch (Exception e) {
            log.error("Error listing PredictWin users error={}", e.getMessage(), e);
            return ResponseEntity.ok(new java.util.HashMap<String, Object>() {
                {
                    put("code", 200);
                    put("success", false);
                    put("message", "Internal server error: " + e.getMessage());
                    put("data", null);
                }
            });
        }
    }
}