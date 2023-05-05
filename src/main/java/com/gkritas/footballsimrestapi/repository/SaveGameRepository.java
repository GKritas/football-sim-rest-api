package com.gkritas.footballsimrestapi.repository;

import com.gkritas.footballsimrestapi.model.SaveGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaveGameRepository extends JpaRepository<SaveGame, Long> {
}
