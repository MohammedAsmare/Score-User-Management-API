package com.soccerusermanagement.config;

import com.soccerusermanagement.auth.entity.User;
import com.soccerusermanagement.auth.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            log.info("Initializing demo user...");
            User demoUser = new User();
            demoUser.setUsername("demo");
            demoUser.setEmail("demo@example.com");
            demoUser.setPasswordHash(passwordEncoder.encode("demo123"));
            userRepository.save(demoUser);
            log.info("Demo user created - Username: demo, Password: demo123");
        } else {
            log.info("Database already contains users, skipping demo user creation");
        }
    }
}