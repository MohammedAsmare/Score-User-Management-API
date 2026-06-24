package com.soccerusermanagement.mobauth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Mobile authentication request")
public class MobAuthRequest {
    @Schema(description = "User name", example = "John Doe")
    @NotBlank(message = "name is required")
    @Size(max = 100, message = "name must be at most 100 characters")
    private String name;

    @Schema(description = "Profile URL or data", example = "https://example.com/profile.jpg")
    @Size(max = 255, message = "profile must be at most 255 characters")
    private String profile;

    @Schema(description = "Unique user ID from Firebase", example = "firebase_uid_12345")
    @NotBlank(message = "uid is required")
    @Size(max = 100, message = "uid must be at most 100 characters")
    private String uid;

    @Schema(description = "Firebase token", example = "eyJhbGciOiJSUzI1NiIsImtpZCI6...")
    @NotBlank(message = "firebaseToken is required")
    @Size(max = 255, message = "firebaseToken must be at most 255 characters")
    private String firebaseToken;

    @Schema(description = "App version", example = "1.0.0")
    @Size(max = 50, message = "appVersion must be at most 50 characters")
    private String appVersion;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }
}
