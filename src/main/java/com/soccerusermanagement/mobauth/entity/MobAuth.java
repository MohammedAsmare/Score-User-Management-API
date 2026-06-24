package com.soccerusermanagement.mobauth.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Entity
@Table(name = "mob_auth")
@Data
@NoArgsConstructor
public class MobAuth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 255)
    private String profile;

    @Column(nullable = false, unique = true, length = 100)
    private String uid;

    @Column(nullable = false, unique = true, length = 255)
    private String firebaseToken;

    @Column(name = "app_version", length = 50)
    private String appVersion;

    @Column(name = "created_at")
    private OffsetDateTime createdAt = OffsetDateTime.now();
}
