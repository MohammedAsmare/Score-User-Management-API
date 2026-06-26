package com.soccerusermanagement.predictwin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Google authentication request for PredictWin")
public class GoogleAuthRequest {
    @NotBlank(message = "UID is required")
    @Schema(description = "Firebase/Google UID", example = "firebase_or_google_uid", required = true)
    private String uid;

    @NotBlank(message = "Name is required")
    @Schema(description = "User display name", example = "Abdi_P", required = true)
    private String name;

    @Schema(description = "Profile image URL", example = "https://...")
    private String profileImage;

    @Schema(description = "FCM token for push notifications", example = "optional_fcm_token")
    private String fcmToken;

    public GoogleAuthRequest() {
    }

    public GoogleAuthRequest(String uid, String name, String profileImage, String fcmToken) {
        this.uid = uid;
        this.name = name;
        this.profileImage = profileImage;
        this.fcmToken = fcmToken;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}