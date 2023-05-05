package com.gkritas.footballsimrestapi.repository;

import com.gkritas.footballsimrestapi.model.Season;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeasonRepository extends JpaRepository<Season, Long>{
}
