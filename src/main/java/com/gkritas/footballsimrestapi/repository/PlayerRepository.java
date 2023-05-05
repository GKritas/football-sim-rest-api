package com.gkritas.footballsimrestapi.repository;

import com.gkritas.footballsimrestapi.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
}
