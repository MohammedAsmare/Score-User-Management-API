package com.predictwin.auth.service;

import com.predictwin.auth.dto.AuthResponse;
import com.predictwin.auth.dto.LoginRequest;
import com.predictwin.auth.dto.RegisterRequest;
import com.predictwin.auth.entity.User;
import com.predictwin.auth.repository.UserRepository;
import com.predictwin.config.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse register(RegisterRequest req) {
        if (userRepository.findByEmail(req.getEmail()).isPresent()) {
            return new AuthResponse(false, "Email already in use", null);
        }
        if (userRepository.findByUsername(req.getUsername()).isPresent()) {
            return new AuthResponse(false, "Username already in use", null);
        }

        User u = new User();
        u.setUsername(req.getUsername());
        u.setEmail(req.getEmail());
        u.setPasswordHash(passwordEncoder.encode(req.getPassword()));
        User saved = userRepository.save(u);

        HashMap<String, Object> data = new HashMap<>();
        data.put("id", saved.getId());
        data.put("username", saved.getUsername());
        data.put("email", saved.getEmail());

        return new AuthResponse(true, "User registered successfully", data);
    }

    public AuthResponse login(LoginRequest req) {
        return userRepository.findByEmail(req.getEmail())
                .map(u -> {
                    if (passwordEncoder.matches(req.getPassword(), u.getPasswordHash())) {
                        String token = jwtUtil.generateToken(u.getId(), u.getUsername());
                        HashMap<String, Object> data = new HashMap<>();
                        HashMap<String, Object> user = new HashMap<>();
                        user.put("id", u.getId());
                        user.put("username", u.getUsername());
                        data.put("token", token);
                        data.put("user", user);
                        return new AuthResponse(true, "Login successful", data);
                    }
                    return new AuthResponse(false, "Invalid credentials", null);
                })
                .orElse(new AuthResponse(false, "Invalid credentials", null));
    }
}
