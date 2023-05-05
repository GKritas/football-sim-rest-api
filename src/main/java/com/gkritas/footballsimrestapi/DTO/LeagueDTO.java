package com.gkritas.footballsimrestapi.DTO;

import com.gkritas.footballsimrestapi.model.League;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeagueDTO {
    private Long id;
    private String name;
    private Long saveGameId;
    private List<TeamDTO> teams;
    private List<SeasonDTO> seasons;

}
