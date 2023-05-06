package com.gkritas.footballsimrestapi.service;

import com.gkritas.footballsimrestapi.exception.SeasonNotFoundException;
import com.gkritas.footballsimrestapi.model.League;
import com.gkritas.footballsimrestapi.model.Match;
import com.gkritas.footballsimrestapi.model.Season;
import com.gkritas.footballsimrestapi.model.Team;
import com.gkritas.footballsimrestapi.repository.SeasonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SeasonServiceTest {

    @Mock
    private SeasonRepository seasonRepository;
    @Mock
    private MatchService matchService;

    @InjectMocks
    private SeasonService seasonService;

    private Season season;

    @BeforeEach
    void setUp() {
        season = new Season();
        season.setId(1L);
        season.setYear(2023);
    }

    @Test
    void getAllSeasons() {
        when(seasonRepository.findAll()).thenReturn(List.of(season));

        List<Season> result = seasonService.getAllSeasons();

        assertEquals(1, result.size());
        assertEquals(season, result.get(0));
    }

    @Test
    void getSeasonById() {
        when(seasonRepository.findById(1L)).thenReturn(Optional.of(season));

        Season result = seasonService.getSeasonById(1L);

        assertEquals(season, result);
    }

    @Test
    void getSeasonById_notFound() {
        when(seasonRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(SeasonNotFoundException.class, ()-> seasonService.getSeasonById(1L));
    }

    @Test
    void deleteSeason() {
        when(seasonRepository.findById(1L)).thenReturn(Optional.of(season));

        seasonService.deleteSeason(1L);

        verify(seasonRepository, times(1)).delete(season);
    }

    @Test
    void createSeason() {
        when(seasonRepository.save(season)).thenReturn(season);

        Season result = seasonService.createSeason(season);

        assertEquals(season, result);
    }

    @Test
    void updateSeason() {
        Season updatedSeason = new Season();
        updatedSeason.setId(1L);
        updatedSeason.setYear(2024);

        when(seasonRepository.findById(1L)).thenReturn(Optional.of(season));
        when(seasonRepository.save(season)).thenReturn(updatedSeason);

        Season result = seasonService.updateSeason(1L, updatedSeason);

        assertEquals(updatedSeason, result);
    }

    @Test
    void generateMatches() {
        League league = new League();
        List<Team> teams = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            Team team = new Team();
            team.setId((long) i);
            teams.add(team);
        }
        league.setTeams(teams);
        season.setLeague(league);

        when(matchService.createMatch(any(Match.class))).thenAnswer(invocation -> invocation.getArgument(0, Match.class));

        List<Match> result = seasonService.generateMatches(season);

        assertEquals(12, result.size());
        verify(matchService, times(12)).createMatch(any(Match.class));

    }
}