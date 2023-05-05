package com.gkritas.footballsimrestapi.DTO;

import com.gkritas.footballsimrestapi.model.Match;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchDTO {
    private Long id;
    private Long seasonId;
    private Long homeTeamId;
    private Long awayTeamId;
    private Integer homeTeamGoals;
    private Integer awayTeamGoals;
    private LocalDate matchDate;
}
