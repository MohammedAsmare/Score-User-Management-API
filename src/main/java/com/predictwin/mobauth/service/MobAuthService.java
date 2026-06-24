package com.predictwin.mobauth.service;

import com.predictwin.auth.dto.AuthResponse;
import com.predictwin.auth.entity.User;
import com.predictwin.auth.repository.UserRepository;
import com.predictwin.config.JwtUtil;
import com.predictwin.mobauth.dto.MobAuthRequest;
import com.predictwin.mobauth.entity.MobAuth;
import com.predictwin.mobauth.repository.MobAuthRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class MobAuthService {
    private static final Logger log = LoggerFactory.getLogger(MobAuthService.class);

    private final MobAuthRepository mobAuthRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public MobAuthService(MobAuthRepository mobAuthRepository, UserRepository userRepository,
            PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.mobAuthRepository = mobAuthRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse registerOrLogin(MobAuthRequest req) {
        log.info("MobAuth request received uid={} name={}", req.getUid(), req.getName());

        Optional<MobAuth> existingMobAuth = mobAuthRepository.findByUid(req.getUid());

        MobAuth mobAuth;
        if (existingMobAuth.isPresent()) {
            mobAuth = existingMobAuth.get();
            mobAuth.setName(req.getName());
            mobAuth.setProfile(req.getProfile());
            mobAuth.setFirebaseToken(req.getFirebaseToken());
            mobAuth.setAppVersion(req.getAppVersion());
            log.info("Updating existing MobAuth record uid={}", req.getUid());
        } else {
            mobAuth = new MobAuth();
            mobAuth.setName(req.getName());
            mobAuth.setProfile(req.getProfile());
            mobAuth.setUid(req.getUid());
            mobAuth.setFirebaseToken(req.getFirebaseToken());
            mobAuth.setAppVersion(req.getAppVersion());
            log.info("Creating new MobAuth record uid={}", req.getUid());
        }

        mobAuthRepository.save(mobAuth);

        Optional<User> existingUser = userRepository.findByEmail(req.getUid() + "@mob.auth");
        User user;
        if (existingUser.isPresent()) {
            user = existingUser.get();
            log.info("Using existing user for mob_auth userId={}", user.getId());
        } else {
            user = new User();
            user.setUsername(req.getName());
            user.setEmail(req.getUid() + "@mob.auth");
            user.setPasswordHash(passwordEncoder.encode(req.getUid() + "_" + System.currentTimeMillis()));
            user.setTotalPoints(0);
            User savedUser = userRepository.save(user);
            user = savedUser;
            log.info("Created new user for mob_auth userId={}", user.getId());
        }

        String token = jwtUtil.generateToken(user.getId(), user.getUsername());

        HashMap<String, Object> data = new HashMap<>();
        HashMap<String, Object> userData = new HashMap<>();
        userData.put("id", user.getId());
        userData.put("username", user.getUsername());
        userData.put("email", user.getEmail());
        data.put("token", token);
        data.put("user", userData);

        return new AuthResponse(true, "Mobile authentication successful", data);
    }

    public List<MobAuth> getAll() {
        log.debug("Fetching all mob_auth records");
        return mobAuthRepository.findAll();
    }

    public Optional<MobAuth> findByUid(String uid) {
        return mobAuthRepository.findByUid(uid);
    }
}
