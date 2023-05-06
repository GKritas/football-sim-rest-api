package com.gkritas.footballsimrestapi.mapper;

import com.gkritas.footballsimrestapi.DTO.TeamDTO;
import com.gkritas.footballsimrestapi.model.League;
import com.gkritas.footballsimrestapi.model.Team;
import com.gkritas.footballsimrestapi.service.LeagueService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeamMapperTest {

    @Mock
    private LeagueService leagueService;
    @InjectMocks
    private TeamMapper teamMapper;

    private Team team;
    private TeamDTO teamDTO;
    private League league;
    @BeforeEach
    void setUp() {
        league = new League();
        league.setId(1L);
        league.setName("Test League");

        team = new Team();
        team.setId(1L);
        team.setName("Test Team");
        team.setLeague(league);

        teamDTO = new TeamDTO();
        teamDTO.setId(1L);
        teamDTO.setName("Test Team");
        teamDTO.setLeagueId(1L);
    }
    @Test
    void toDTO() {
        TeamDTO result = teamMapper.toDTO(team);

        assertEquals(teamDTO.getId(), result.getId());
        assertEquals(teamDTO.getName(), result.getName());
        assertEquals(teamDTO.getLeagueId(), result.getLeagueId());
    }

    @Test
    void toEntity() {
        when(leagueService.getLeagueById(1L)).thenReturn(league);

        Team result = teamMapper.toEntity(teamDTO);

        assertEquals(team.getId(), result.getId());
        assertEquals(team.getName(), result.getName());
        assertEquals(team.getLeague().getId(), result.getLeague().getId());
    }
}