package com.gkritas.footballsimrestapi.mapper;

import com.gkritas.footballsimrestapi.DTO.LeagueDTO;
import com.gkritas.footballsimrestapi.model.League;
import com.gkritas.footballsimrestapi.model.SaveGame;
import com.gkritas.footballsimrestapi.service.SaveGameService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class LeagueMapper {

    private final SaveGameService saveGameService;
    private final SeasonMapper seasonMapper;
    private final TeamMapper teamMapper;


    public LeagueMapper(SaveGameService saveGameService,
                        SeasonMapper seasonMapper,
                        TeamMapper teamMapper) {
        this.saveGameService = saveGameService;
        this.seasonMapper = seasonMapper;
        this.teamMapper = teamMapper;
    }

    public LeagueDTO toDTO(League league) {
        LeagueDTO dto = new LeagueDTO();
        dto.setId(league.getId());
        dto.setName(league.getName());
        dto.setSaveGameId(league.getSaveGame().getId());
        if (dto.getSeasons() != null) {
            dto.setSeasons(league.getSeasons().stream()
                    .map(seasonMapper::toDTO).collect(Collectors.toList()));
        } else {
            dto.setSeasons(new ArrayList<>());
        }
        if (dto.getTeams() != null) {
            dto.setTeams(league.getTeams().stream()
                    .map(teamMapper::toDTO).collect(Collectors.toList()));
        } else {
            dto.setTeams(new ArrayList<>());
        }

        return dto;
    }

    public League toEntity(LeagueDTO dto) {
        SaveGame saveGame = saveGameService.findSaveGameById(dto.getSaveGameId());
        League league = new League();
        league.setId(dto.getId());
        league.setName(dto.getName());
        league.setSaveGame(saveGame);
        if (league.getSeasons() != null) {
            league.setSeasons(dto.getSeasons().stream()
                    .map(seasonMapper::toEntity).collect(Collectors.toList()));
        } else {
            league.setSeasons(new ArrayList<>());
        }
        if (league.getTeams() != null) {
            league.setTeams(dto.getTeams().stream()
                    .map(teamMapper::toEntity).collect(Collectors.toList()));
        } else {
            league.setTeams(new ArrayList<>());
        }

        return league;
    }
}
