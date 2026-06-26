package com.soccerusermanagement.predictwin.service;

import com.soccerusermanagement.predictwin.dto.GoogleAuthRequest;
import com.soccerusermanagement.predictwin.entity.PredictWinUser;
import com.soccerusermanagement.predictwin.repository.PredictWinUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class PredictWinService {
    private static final Logger log = LoggerFactory.getLogger(PredictWinService.class);

    private final PredictWinUserRepository predictWinUserRepository;

    public PredictWinService(PredictWinUserRepository predictWinUserRepository) {
        this.predictWinUserRepository = predictWinUserRepository;
    }

    public Map<String, Object> googleAuth(GoogleAuthRequest req) {
        log.info("PredictWin GoogleAuth request received uid={} name={}", req.getUid(), req.getName());

        Optional<PredictWinUser> existingUser = predictWinUserRepository.findByUid(req.getUid());
        boolean isNewUser = existingUser.isEmpty();

        PredictWinUser user;
        if (existingUser.isPresent()) {
            user = existingUser.get();
            user.setName(req.getName());
            if (req.getProfileImage() != null) {
                user.setProfileImage(req.getProfileImage());
            }
            if (req.getFcmToken() != null) {
                user.setFcmToken(req.getFcmToken());
            }
            log.info("Updating existing PredictWin user uid={}", req.getUid());
        } else {
            user = new PredictWinUser();
            user.setUid(req.getUid());
            user.setName(req.getName());
            user.setProfileImage(req.getProfileImage());
            user.setFcmToken(req.getFcmToken());
            user.setTotalPoints(0);
            log.info("Creating new PredictWin user uid={}", req.getUid());
        }

        predictWinUserRepository.save(user);

        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("success", true);
        response.put("message", isNewUser ? "Registration successful" : "Login successful");
        response.put("data", user);

        log.info("PredictWin GoogleAuth completed uid={} isNewUser={}", req.getUid(), isNewUser);
        return response;
    }

    public Optional<PredictWinUser> findByUid(String uid) {
        return predictWinUserRepository.findByUid(uid);
    }

    public Iterable<PredictWinUser> getAllUsers() {
        return predictWinUserRepository.findAll();
    }
}