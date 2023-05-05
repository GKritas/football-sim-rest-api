package com.gkritas.footballsimrestapi.mapper;

import com.gkritas.footballsimrestapi.DTO.MatchDTO;
import com.gkritas.footballsimrestapi.model.Match;
import com.gkritas.footballsimrestapi.service.SeasonService;
import com.gkritas.footballsimrestapi.service.TeamService;
import org.springframework.stereotype.Component;

@Component
public class MatchMapper {

    private final SeasonService seasonService;
    private final TeamService teamService;

    public MatchMapper(SeasonService seasonService,
                       TeamService teamService) {
        this.seasonService = seasonService;
        this.teamService = teamService;
    }

    public MatchDTO toDTO(Match match) {
        MatchDTO dto = new MatchDTO();
        dto.setId(match.getId());
        dto.setSeasonId(match.getSeason().getId());
        dto.setHomeTeamId(match.getHomeTeam().getId());
        dto.setAwayTeamId(match.getAwayTeam().getId());
        dto.setHomeTeamGoals(match.getHomeTeamGoals());
        dto.setAwayTeamGoals(match.getAwayTeamGoals());
        dto.setMatchDate(match.getMatchDate());

        return dto;
    }

    public Match toEntity(MatchDTO dto) {
        Match match = new Match();
        match.setId(dto.getId());
        match.setSeason(seasonService.getSeasonById(dto.getSeasonId()));
        match.setHomeTeam(teamService.getTeamById(dto.getHomeTeamId()));
        match.setAwayTeam(teamService.getTeamById(dto.getAwayTeamId()));
        match.setHomeTeamGoals(dto.getHomeTeamGoals());
        match.setAwayTeamGoals(dto.getAwayTeamGoals());
        match.setMatchDate(dto.getMatchDate());

        return match;
    }
}
