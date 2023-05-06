package com.gkritas.footballsimrestapi.service;

import com.gkritas.footballsimrestapi.exception.TeamNotFoundException;
import com.gkritas.footballsimrestapi.model.Team;
import com.gkritas.footballsimrestapi.repository.TeamRepository;
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
class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;
    @InjectMocks
    private TeamService teamService;

    private Team team;

    @BeforeEach
    void setUp() {
        team = new Team();
        team.setId(1L);
        team.setName("Test Team");
    }

    @Test
    void getAllTeams() {
        when(teamRepository.findAll()).thenReturn(List.of(team));

        List<Team> result = teamService.getAllTeams();

        assertEquals(1, result.size());
        assertEquals(team, result.get(0));
    }

    @Test
    void getTeamById() {
        when(teamRepository.findById(1L)).thenReturn(Optional.of(team));

        Team result = teamService.getTeamById(1L);

        assertEquals(team, result);
    }

    @Test
    void getTeamById_notFound() {
        when(teamRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TeamNotFoundException.class, ()-> teamService.getTeamById(1L));
    }

    @Test
    void deleteTeamById() {
        when(teamRepository.findById(1L)).thenReturn(Optional.of(team));

        teamService.deleteTeamById(1L);

        verify(teamRepository, times(1)).delete(team);
    }

    @Test
    void createTeam() {
        when(teamRepository.save(team)).thenReturn(team);

        Team result = teamService.createTeam(team);

        assertEquals(team, result);
    }

    @Test
    void updateTeam() {
        Team updatedTeam = new Team();
        updatedTeam.setId(1L);
        updatedTeam.setName("Updated Test Team");

        when(teamRepository.findById(1L)).thenReturn(Optional.of(team));
        when(teamRepository.save(team)).thenReturn(updatedTeam);

        Team result = teamService.updateTeam(1L, updatedTeam);

        assertEquals(updatedTeam, result);
    }
}