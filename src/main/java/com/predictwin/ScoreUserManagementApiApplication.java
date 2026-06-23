package com.predictwin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class ScoreUserManagementApiApplication {
    private static final Logger log = LoggerFactory.getLogger(ScoreUserManagementApiApplication.class);

    public static void main(String[] args) {
        log.info("Starting Score User Management API");
        SpringApplication.run(ScoreUserManagementApiApplication.class, args);
        log.info("Score User Management API started successfully");
    }
}
