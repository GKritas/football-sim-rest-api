package com.gkritas.footballsimrestapi.mapper;

import com.gkritas.footballsimrestapi.DTO.TeamDTO;
import com.gkritas.footballsimrestapi.model.Team;
import com.gkritas.footballsimrestapi.service.LeagueService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class TeamMapper {

    private final LeagueService leagueService;
    private final PlayerMapper playerMapper;

    public TeamMapper(LeagueService leagueService,
                      PlayerMapper playerMapper) {
        this.leagueService = leagueService;
        this.playerMapper = playerMapper;
    }

    public TeamDTO toDTO(Team team) {
        TeamDTO dto = new TeamDTO();
        dto.setId(team.getId());
        dto.setName(team.getName());
        dto.setDefence(team.getDefence());
        dto.setMidfield(team.getMidfield());
        dto.setAttack(team.getAttack());
        dto.setPoints(team.getPoints());
        dto.setGoalsScored(team.getGoalsScored());
        dto.setGoalsConceded(team.getGoalsConceded());
        dto.setWins(team.getWins());
        dto.setDraws(team.getDraws());
        dto.setLosses(team.getLosses());
        dto.setLeagueId(team.getLeague().getId());
        if (team.getPlayers() != null) {
            dto.setPlayers(team.getPlayers()
                    .stream()
                    .map(playerMapper::toDTO).collect(Collectors.toList()));
        } else {
            dto.setPlayers(new ArrayList<>());
        }

        return dto;
    }

    public Team toEntity(TeamDTO dto) {
        Team team = new Team();
        team.setId(dto.getId());
        team.setName(dto.getName());
        team.setDefence(dto.getDefence());
        team.setMidfield(dto.getMidfield());
        team.setAttack(dto.getAttack());
        team.setPoints(dto.getPoints());
        team.setGoalsScored(dto.getGoalsScored());
        team.setGoalsConceded(dto.getGoalsConceded());
        team.setWins(dto.getWins());
        team.setDraws(dto.getDraws());
        team.setLosses(dto.getLosses());
        team.setLeague(leagueService.getLeagueById(dto.getLeagueId()));
        if (dto.getPlayers() != null) {
            team.setPlayers(dto.getPlayers()
                    .stream()
                    .map(playerMapper::toEntity).collect(Collectors.toList()));
        } else {
            team.setPlayers(new ArrayList<>());
        }

        return team;
    }
}
