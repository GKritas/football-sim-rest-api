package com.gkritas.footballsimrestapi.controller;

import com.gkritas.footballsimrestapi.DTO.PlayerDTO;
import com.gkritas.footballsimrestapi.DTO.TeamDTO;
import com.gkritas.footballsimrestapi.mapper.TeamMapper;
import com.gkritas.footballsimrestapi.model.Player;
import com.gkritas.footballsimrestapi.model.Team;
import com.gkritas.footballsimrestapi.modelAssembler.PlayerModelAssembler;
import com.gkritas.footballsimrestapi.modelAssembler.TeamModelAssembler;
import com.gkritas.footballsimrestapi.service.TeamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@ExtendWith(MockitoExtension.class)
class TeamControllerTest {
    @Mock
    private TeamService teamService;
    @Mock
    private TeamModelAssembler teamModelAssembler;
    @Mock
    private TeamMapper teamMapper;
    @Mock
    private PlayerModelAssembler playerModelAssembler;

    @InjectMocks
    private TeamController teamController;

    private Team team;
    private TeamDTO teamDTO;
    private EntityModel<TeamDTO> teamModel;
    private List<Team> teams;
    private Player player;
    private PlayerDTO playerDTO;
    private EntityModel<PlayerDTO> playerModel;
    private List<Player> players;

    @BeforeEach
    void setUp() {
        team = new Team();
        team.setId(1L);
        team.setName("Test Team");

        teamDTO = new TeamDTO();
        teamDTO.setId(1L);
        teamDTO.setName("Test Team");

        Link selfTeamLink = linkTo(methodOn(TeamController.class).getSingleTeam(1L)).withSelfRel();
        Link allTeamsLink = linkTo(methodOn(TeamController.class).getAllTeams()).withRel("teams");
        teamModel = EntityModel.of(teamDTO, selfTeamLink, allTeamsLink);
        teams = List.of(team);

        player = new Player();
        player.setId(1L);
        player.setFirstName("Test");
        player.setLastName("Player");

        playerDTO = new PlayerDTO();
        playerDTO.setId(1L);
        playerDTO.setFirstName("Test");
        playerDTO.setLastName("Player");

        Link selfPlayerLink = linkTo(methodOn(PlayerController.class).getSinglePlayer(1L)).withSelfRel();
        Link allPlayersLink = linkTo(methodOn(PlayerController.class).getAllPlayers()).withRel("players");
        playerModel = EntityModel.of(playerDTO, selfPlayerLink, allPlayersLink);
        players = List.of(player);
        team.setPlayers(players);
    }
    @Test
    void getAllTeams() {
        when(teamService.getAllTeams()).thenReturn(teams);
        when(teamModelAssembler.toModel(team)).thenReturn(teamModel);

        ResponseEntity<?> response = teamController.getAllTeams();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(teamService, times(1)).getAllTeams();
        verify(teamModelAssembler, times(teams.size())).toModel(any(Team.class));
    }

    @Test
    void getSingleTeam() {
        when(teamService.getTeamById(1L)).thenReturn(team);
        when(teamModelAssembler.toModel(team)).thenReturn(teamModel);

        ResponseEntity<?> response = teamController.getSingleTeam(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(teamService, times(1)).getTeamById(1L);
        verify(teamModelAssembler, times(1)).toModel(team);
    }

    @Test
    void deleteTeam() {
        doNothing().when(teamService).deleteTeamById(1L);

        ResponseEntity<?> response = teamController.deleteTeam(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(teamService, times(1)).deleteTeamById(1L);
    }

    @Test
    void createTeam() {
        when(teamMapper.toEntity(teamDTO)).thenReturn(team);
        when(teamService.createTeam(team)).thenReturn(team);
        when(teamModelAssembler.toModel(team)).thenReturn(teamModel);

        ResponseEntity<?> response = teamController.createTeam(teamDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(teamMapper, times(1)).toEntity(teamDTO);
        verify(teamService, times(1)).createTeam(team);
        verify(teamModelAssembler, times(1)).toModel(team);
    }

    @Test
    void updateTeam() {
        when(teamMapper.toEntity(teamDTO)).thenReturn(team);
        when(teamService.updateTeam(1L, team)).thenReturn(team);
        when(teamModelAssembler.toModel(team)).thenReturn(teamModel);

        ResponseEntity<?> response = teamController.updateTeam(1L, teamDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(teamMapper, times(1)).toEntity(teamDTO);
        verify(teamService, times(1)).updateTeam(1L, team);
        verify(teamModelAssembler, times(1)).toModel(team);
    }

    @Test
    void getPlayersOfTeam() {
        when(teamService.getTeamById(1L)).thenReturn(team);
        when(playerModelAssembler.toModel(player)).thenReturn(playerModel);

        ResponseEntity<?> response = teamController.getPlayersOfTeam(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(teamService, times(1)).getTeamById(1L);
        verify(playerModelAssembler, times(players.size())).toModel(any(Player.class));
    }
}