package com.gkritas.footballsimrestapi.DTO;

import com.gkritas.footballsimrestapi.model.Team;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamDTO {
    private Long id;
    private String name;
    private Integer defence;
    private Integer midfield;
    private Integer attack;
    private Integer points;
    private Integer goalsScored;
    private Integer goalsConceded;
    private Integer wins;
    private Integer draws;
    private Integer losses;
    private Long leagueId;
    private List<PlayerDTO> players;

}
