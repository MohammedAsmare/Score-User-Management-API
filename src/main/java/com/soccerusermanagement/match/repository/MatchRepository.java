package com.soccerusermanagement.match.repository;

import com.soccerusermanagement.match.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long> {
}
