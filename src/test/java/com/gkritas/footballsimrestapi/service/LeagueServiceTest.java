package com.gkritas.footballsimrestapi.service;

import com.gkritas.footballsimrestapi.exception.LeagueNotFoundException;
import com.gkritas.footballsimrestapi.model.League;
import com.gkritas.footballsimrestapi.repository.LeagueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LeagueServiceTest {

    @Mock
    private LeagueRepository leagueRepository;

    @InjectMocks
    private LeagueService leagueService;

    private League league;

    @BeforeEach
    void setUp() {
        league = new League();
        league.setId(1L);
        league.setName("Test League");
    }
    @Test
    void getAllLeagues() {
        when(leagueRepository.findAll()).thenReturn(List.of(league));

        List<League> result = leagueService.getAllLeagues();

        assertEquals(1, result.size());
        assertEquals(league, result.get(0));
    }

    @Test
    void getLeagueById() {
        when(leagueRepository.findById(1L)).thenReturn(Optional.of(league));

        League result = leagueService.getLeagueById(1L);

        assertEquals(league, result);
    }

    @Test
    void getLeagueById_notFound() {
        when(leagueRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(LeagueNotFoundException.class, ()-> leagueService.getLeagueById(1L));
    }

    @Test
    void deleteLeague() {
        when(leagueRepository.findById(1L)).thenReturn(Optional.of(league));

        leagueService.deleteLeague(1L);

        verify(leagueRepository, times(1)).delete(league);
    }

    @Test
    void createLeague() {
        when(leagueRepository.save(league)).thenReturn(league);

        League result = leagueService.createLeague(league);

        assertEquals(league, result);
    }

    @Test
    void updateLeague() {
        League updatedLeague = new League();
        updatedLeague.setId(1L);
        updatedLeague.setName("Updated Test League");

        when(leagueRepository.findById(1L)).thenReturn(Optional.of(league));
        when(leagueRepository.save(league)).thenReturn(updatedLeague);

        League result = leagueService.updateLeague(1L, updatedLeague);

        assertEquals(updatedLeague, result);
    }
}