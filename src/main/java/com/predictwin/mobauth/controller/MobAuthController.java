package com.predictwin.mobauth.controller;

import com.predictwin.auth.dto.AuthResponse;
import com.predictwin.mobauth.dto.MobAuthRequest;
import com.predictwin.mobauth.entity.MobAuth;
import com.predictwin.mobauth.service.MobAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/mob-auth")
@Tag(name = "Mobile Auth", description = "Mobile authentication endpoints")
public class MobAuthController {
    private final MobAuthService mobAuthService;
    private static final Logger log = LoggerFactory.getLogger(MobAuthController.class);

    public MobAuthController(MobAuthService mobAuthService) {
        this.mobAuthService = mobAuthService;
    }

    @Operation(summary = "Mobile authentication - register or login with Firebase")
    @PostMapping
    public ResponseEntity<?> mobAuth(@Valid @RequestBody MobAuthRequest req) {
        log.info("POST /api/mob-auth - request received uid={} name={}", req.getUid(), req.getName());
        try {
            AuthResponse response = mobAuthService.registerOrLogin(req);
            if (response.isSuccess()) {
                log.info("MobAuth successful uid={}", req.getUid());
                return ResponseEntity.ok(response);
            } else {
                log.warn("MobAuth failed uid={} reason={}", req.getUid(), response.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            log.error("MobAuth error uid={} error={}", req.getUid(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AuthResponse(false, "Internal server error: " + e.getMessage(), null));
        }
    }

    @Operation(summary = "Get all mobile auth records")
    @GetMapping
    public ResponseEntity<?> getAllMobAuths() {
        log.info("GET /api/mob-auth - list all records");
        try {
            List<MobAuth> all = mobAuthService.getAll();
            log.info("Listed mob_auth records count={}", all.size());
            return ResponseEntity.ok(new HashMap<String, Object>() {
                {
                    put("success", true);
                    put("data", all);
                }
            });
        } catch (Exception e) {
            log.error("Error listing mob_auth records error={}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AuthResponse(false, "Internal server error: " + e.getMessage(), null));
        }
    }

    @Operation(summary = "Get mobile auth record by UID")
    @GetMapping("/uid/{uid}")
    public ResponseEntity<?> getByUid(@PathVariable String uid) {
        log.info("GET /api/mob-auth/uid/{} - request", uid);
        try {
            Optional<MobAuth> mobAuthOpt = mobAuthService.findByUid(uid);
            if (mobAuthOpt.isPresent()) {
                MobAuth mobAuth = mobAuthOpt.get();
                HashMap<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("data", mobAuth);
                return ResponseEntity.ok(response);
            } else {
                log.warn("MobAuth record not found for uid={}", uid);
                AuthResponse errorResponse = new AuthResponse(false, "Record not found for uid: " + uid, null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
        } catch (Exception e) {
            log.error("Error fetching mob_auth by uid={} error={}", uid, e.getMessage(), e);
            AuthResponse errorResponse = new AuthResponse(false, "Internal server error: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
