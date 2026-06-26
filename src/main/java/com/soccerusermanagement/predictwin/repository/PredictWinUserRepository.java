package com.soccerusermanagement.predictwin.repository;

import com.soccerusermanagement.predictwin.entity.PredictWinUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PredictWinUserRepository extends JpaRepository<PredictWinUser, Long> {
    Optional<PredictWinUser> findByUid(String uid);

    boolean existsByUid(String uid);
}