package com.gkritas.footballsimrestapi.mapper;

import com.gkritas.footballsimrestapi.DTO.SeasonDTO;
import com.gkritas.footballsimrestapi.model.Season;
import com.gkritas.footballsimrestapi.service.LeagueService;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class SeasonMapper {

    private final LeagueService leagueService;
    private final MatchMapper matchMapper;

    public SeasonMapper(LeagueService leagueService,
                        MatchMapper matchMapper) {
        this.leagueService = leagueService;
        this.matchMapper = matchMapper;
    }

    public SeasonDTO toDTO(Season season) {
        SeasonDTO dto = new SeasonDTO();
        dto.setId(season.getId());
        dto.setYear(season.getYear());
        dto.setStartDate(season.getStartDate());
        dto.setEndDate(season.getEndDate());
        dto.setLeagueId(season.getLeague().getId());
        dto.setMatches(season.getMatches()
                .stream()
                .map(matchMapper::toDTO).collect(Collectors.toList()));

        return dto;
    }

    public Season toEntity(SeasonDTO dto) {
        Season season = new Season();
        season.setId(dto.getId());
        season.setYear(dto.getYear());
        season.setStartDate(dto.getStartDate());
        season.setEndDate(dto.getEndDate());
        season.setLeague(leagueService.getLeagueById(dto.getLeagueId()));
        season.setMatches(dto.getMatches()
                .stream()
                .map(matchMapper::toEntity).collect(Collectors.toList()));

        return season;
    }
}
