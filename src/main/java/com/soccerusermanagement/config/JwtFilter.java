package com.soccerusermanagement.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(JwtFilter.class);

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        String method = request.getMethod();

        // Skip JWT validation for:
        // 1. Swagger endpoints
        // 2. Token endpoints
        // 3. All GET requests (read-only operations)
        // 4. DELETE requests
        if (path.startsWith("/swagger-ui/") ||
                path.startsWith("/v3/api-docs") ||
                path.startsWith("/swagger-resources") ||
                path.startsWith("/swagger-config") ||
                path.startsWith("/webjars/") ||
                path.startsWith("/api/token/") ||
                "GET".equalsIgnoreCase(method) ||
                "DELETE".equalsIgnoreCase(method) ||
                "OPTIONS".equalsIgnoreCase(method)) {
            log.debug("Skipping JWT filter for endpoint: {} method={}", path, method);
            filterChain.doFilter(request, response);
            return;
        }

        // Try to get token from custom "token" header first
        String token = request.getHeader("token");

        // If not found, try to get from Authorization: Bearer header
        if (token == null || token.trim().isEmpty()) {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
            }
        } else if (token.startsWith("Bearer ")) {
            // Handle case where token header contains "Bearer " prefix
            token = token.substring(7);
        }

        // If token is still missing, reject the request
        if (token == null || token.trim().isEmpty()) {
            log.warn("Missing token header for method={} uri={}", request.getMethod(), request.getRequestURI());
            SecurityContextHolder.clearContext();
            sendErrorResponse(response, 555, "Token is missing");
            return;
        }

        try {
            Claims claims = jwtUtil.parseClaims(token);
            String userId = claims.getSubject();
            log.debug("JWT authenticated request method={} uri={} userId={}", request.getMethod(),
                    request.getRequestURI(), userId);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userId, null,
                    Collections.emptyList());
            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (io.jsonwebtoken.ExpiredJwtException ex) {
            log.warn("JWT token expired for method={} uri={} reason={}", request.getMethod(),
                    request.getRequestURI(), ex.getMessage());
            SecurityContextHolder.clearContext();
            sendErrorResponse(response, 555, "Token has expired");
            return;
        } catch (Exception ex) {
            log.warn("JWT authentication failed for method={} uri={} reason={}", request.getMethod(),
                    request.getRequestURI(), ex.getMessage());
            SecurityContextHolder.clearContext();
            sendErrorResponse(response, 555, "Token is invalid");
            return;
        }
        filterChain.doFilter(request, response);
    }

    private void sendErrorResponse(HttpServletResponse response, int code, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("code", code);
        errorResponse.put("success", false);
        errorResponse.put("message", message);
        objectMapper.writeValue(response.getWriter(), errorResponse);
    }
}
