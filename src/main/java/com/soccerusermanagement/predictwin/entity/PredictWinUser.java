package com.soccerusermanagement.predictwin.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Entity
@Table(name = "predictwin_users")
@Data
@NoArgsConstructor
public class PredictWinUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String uid;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 255)
    private String profileImage;

    @Column(length = 255)
    private String fcmToken;

    @Column(name = "total_points", nullable = false)
    private Integer totalPoints = 0;

    @Column(name = "created_at")
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt = OffsetDateTime.now();

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = OffsetDateTime.now();
    }
}