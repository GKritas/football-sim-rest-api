package com.gkritas.footballsimrestapi.mapper;

import com.gkritas.footballsimrestapi.DTO.SeasonDTO;
import com.gkritas.footballsimrestapi.model.League;
import com.gkritas.footballsimrestapi.model.Match;
import com.gkritas.footballsimrestapi.model.Season;
import com.gkritas.footballsimrestapi.service.LeagueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SeasonMapperTest {

    @Mock
    private LeagueService leagueService;

    @InjectMocks
    private SeasonMapper seasonMapper;

    private Season season;
    private SeasonDTO seasonDTO;
    private League league;
    @BeforeEach
    void setUp() {
        league = new League();
        league.setId(1L);
        league.setName("Test League");

        season = new Season();
        season.setId(1L);
        season.setYear(2023);
        season.setLeague(league);

        seasonDTO = new SeasonDTO();
        seasonDTO.setId(1L);
        seasonDTO.setYear(2023);
        seasonDTO.setLeagueId(1L);
    }
    @Test
    void toDTO() {
        SeasonDTO result = seasonMapper.toDTO(season);

        assertEquals(seasonDTO.getId(), result.getId());
        assertEquals(seasonDTO.getYear(), result.getYear());
        assertEquals(seasonDTO.getLeagueId(), result.getLeagueId());
    }

    @Test
    void toEntity() {
        when(leagueService.getLeagueById(1L)).thenReturn(league);

        Season result = seasonMapper.toEntity(seasonDTO);

        assertEquals(season.getId(), result.getId());
        assertEquals(season.getYear(), result.getYear());
        assertEquals(season.getLeague().getId(), result.getLeague().getId());
    }
}