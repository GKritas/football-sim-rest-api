package com.gkritas.footballsimrestapi.repository;

import com.gkritas.footballsimrestapi.model.Match;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {
    @Query("SELECT m FROM Match m WHERE (m.homeTeam.id = :id OR m.awayTeam.id = :id) AND m.matchDate < CURRENT_DATE ORDER BY m.matchDate DESC")
    List<Match> findLastFiveMatchesByTeam(@Param("id") Long id, Pageable pageable);
}
