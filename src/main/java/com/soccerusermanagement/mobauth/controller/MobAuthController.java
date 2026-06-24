package com.soccerusermanagement.mobauth.controller;

import com.soccerusermanagement.auth.dto.AuthResponse;
import com.soccerusermanagement.mobauth.dto.MobAuthRequest;
import com.soccerusermanagement.mobauth.entity.MobAuth;
import com.soccerusermanagement.mobauth.service.MobAuthService;
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
            MobAuth mobAuth = mobAuthService.registerOrLogin(req);
            log.info("MobAuth completed uid={} mobAuthId={}", req.getUid(), mobAuth.getId());
            String message = "login".equals(mobAuth.getAppVersion()) ? "Login successful" : "Registration successful";
            HashMap<String, Object> response = new HashMap<>();
            response.put("code", 200);
            response.put("success", true);
            response.put("message", message);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("MobAuth error uid={} error={}", req.getUid(), e.getMessage(), e);
            return ResponseEntity.ok(new HashMap<String, Object>() {
                {
                    put("code", 200);
                    put("success", false);
                    put("message", "Internal server error: " + e.getMessage());
                }
            });
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
                    put("code", 200);
                    put("success", true);
                    put("data", all);
                }
            });
        } catch (Exception e) {
            log.error("Error listing mob_auth records error={}", e.getMessage(), e);
            return ResponseEntity.ok(new HashMap<String, Object>() {
                {
                    put("code", 200);
                    put("success", false);
                    put("message", "Internal server error: " + e.getMessage());
                    put("data", null);
                }
            });
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
                response.put("code", 200);
                response.put("success", true);
                response.put("data", mobAuth);
                return ResponseEntity.ok(response);
            } else {
                log.warn("MobAuth record not found for uid={}", uid);
                return ResponseEntity.ok(new HashMap<String, Object>() {
                    {
                        put("code", 404);
                        put("success", false);
                        put("message", "Record not found for uid: " + uid);
                        put("data", null);
                    }
                });
            }
        } catch (Exception e) {
            log.error("Error fetching mob_auth by uid={} error={}", uid, e.getMessage(), e);
            return ResponseEntity.ok(new HashMap<String, Object>() {
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
