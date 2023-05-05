package com.gkritas.footballsimrestapi.DTO;

import com.gkritas.footballsimrestapi.model.Season;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeasonDTO {
    private Long id;
    private Integer year;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long leagueId;
    private List<MatchDTO> matches;
}
