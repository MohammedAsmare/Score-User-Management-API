package com.soccerusermanagement.token.service;

import com.soccerusermanagement.auth.entity.User;
import com.soccerusermanagement.auth.repository.UserRepository;
import com.soccerusermanagement.config.JwtUtil;
import com.soccerusermanagement.token.dto.TokenRequest;
import com.soccerusermanagement.token.dto.TokenResponse;
import com.soccerusermanagement.token.dto.TokenValidationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Service
public class TokenService {
    private static final Logger log = LoggerFactory.getLogger(TokenService.class);

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public TokenService(JwtUtil jwtUtil, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public TokenResponse generateToken(TokenRequest request) {
        log.debug("Generating token for username={}", request.getUsername());
        try {
            User user = userRepository.findByUsername(request.getUsername())
                    .orElse(null);

            if (user == null) {
                log.warn("Token generation failed: user not found username={}", request.getUsername());
                return new TokenResponse(401, false, "Invalid credentials", null, null);
            }

            if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
                log.warn("Token generation failed: invalid password for username={}", request.getUsername());
                return new TokenResponse(401, false, "Invalid credentials", null, null);
            }

            String token = jwtUtil.generateToken(user.getId(), user.getUsername());
            Date expiration = new Date(System.currentTimeMillis() + jwtUtil.getExpirationMs());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            String expireOn = sdf.format(expiration);

            log.info("Token generated successfully for userId={} username={}", user.getId(), user.getUsername());
            return new TokenResponse(200, true, "Token generated successfully", token, expireOn);
        } catch (Exception e) {
            log.error("Error generating token for username={} error={}", request.getUsername(), e.getMessage(), e);
            return new TokenResponse(500, false, "Failed to generate token: " + e.getMessage(), null, null);
        }
    }

    public TokenValidationResponse validateToken(String token) {
        log.debug("Validating token");
        try {
            if (token == null || token.trim().isEmpty()) {
                log.warn("Token validation failed: token is null or empty");
                return new TokenValidationResponse(400, false, "Token is required");
            }

            var claims = jwtUtil.parseClaims(token);
            String userId = claims.getSubject();
            String username = claims.get("username", String.class);

            log.info("Token validated successfully userId={} username={}", userId, username);
            return new TokenValidationResponse(200, true, "valid token");
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            log.warn("Token validation failed: token expired");
            return new TokenValidationResponse(401, false, "Token has expired");
        } catch (io.jsonwebtoken.JwtException e) {
            log.warn("Token validation failed: invalid token - {}", e.getMessage());
            return new TokenValidationResponse(401, false, "Invalid token");
        } catch (Exception e) {
            log.error("Error validating token error={}", e.getMessage(), e);
            return new TokenValidationResponse(500, false, "Failed to validate token: " + e.getMessage());
        }
    }
}