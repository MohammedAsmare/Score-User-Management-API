package com.predictwin.mobauth.repository;

import com.predictwin.mobauth.entity.MobAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MobAuthRepository extends JpaRepository<MobAuth, Long> {
    Optional<MobAuth> findByUid(String uid);

    Optional<MobAuth> findByFirebaseToken(String firebaseToken);

    boolean existsByUid(String uid);

    boolean existsByFirebaseToken(String firebaseToken);
}