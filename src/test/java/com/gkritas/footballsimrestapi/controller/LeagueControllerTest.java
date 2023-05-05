package com.gkritas.footballsimrestapi.controller;

import com.gkritas.footballsimrestapi.DTO.LeagueDTO;
import com.gkritas.footballsimrestapi.mapper.LeagueMapper;
import com.gkritas.footballsimrestapi.model.League;
import com.gkritas.footballsimrestapi.modelAssembler.LeagueModelAssembler;
import com.gkritas.footballsimrestapi.service.LeagueService;
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
class LeagueControllerTest {

    @Mock
    private LeagueService leagueService;
    @Mock
    private LeagueModelAssembler leagueModelAssembler;
    @Mock
    private LeagueMapper leagueMapper;

    @InjectMocks
    private LeagueController leagueController;

    private League league;
    private LeagueDTO leagueDTO;
    private EntityModel<LeagueDTO> leagueModel;
    private List<League> leagues;

    @BeforeEach
    void setUp() {
        league = new League();
        league.setId(1L);
        league.setName("Test League");

        leagueDTO = new LeagueDTO();
        leagueDTO.setId(1L);
        leagueDTO.setName("Test League");

        Link selfLink = linkTo(methodOn(LeagueController.class).getSingleLeague(league.getId())).withSelfRel();
        Link allLink = linkTo(methodOn(LeagueController.class).getAllLeagues()).withRel("leagues");
        leagueModel = EntityModel.of(leagueDTO, selfLink, allLink);
        leagues = List.of(league);
    }
    @Test
    void getAllLeagues() {
        when(leagueService.getAllLeagues()).thenReturn(leagues);
        when(leagueModelAssembler.toModel(league)).thenReturn(leagueModel);

        ResponseEntity<?> response = leagueController.getAllLeagues();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(leagueService, times(1)).getAllLeagues();
        verify(leagueModelAssembler, times(leagues.size())).toModel(any(League.class));
    }

    @Test
    void getSingleLeague() {
        when(leagueService.getLeagueById(1L)).thenReturn(league);
        when(leagueModelAssembler.toModel(league)).thenReturn(leagueModel);

        ResponseEntity<?> response = leagueController.getSingleLeague(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(leagueService, times(1)).getLeagueById(1L);
        verify(leagueModelAssembler, times(1)).toModel(league);
    }

    @Test
    void deleteLeague() {
        doNothing().when(leagueService).deleteLeague(1L);

        ResponseEntity<?> response = leagueController.deleteLeague(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(leagueService, times(1)).deleteLeague(1L);
    }

    @Test
    void createLeague() {
        when(leagueMapper.toEntity(leagueDTO)).thenReturn(league);
        when(leagueService.createLeague(league)).thenReturn(league);
        when(leagueModelAssembler.toModel(league)).thenReturn(leagueModel);

        ResponseEntity<?> response = leagueController.createLeague(leagueDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(leagueMapper, times(1)).toEntity(leagueDTO);
        verify(leagueService, times(1)).createLeague(league);
        verify(leagueModelAssembler, times(1)).toModel(league);
    }

    @Test
    void updateLeague() {
        when(leagueMapper.toEntity(leagueDTO)).thenReturn(league);
        when(leagueService.updateLeague(1L, league)).thenReturn(league);
        when(leagueModelAssembler.toModel(league)).thenReturn(leagueModel);

        ResponseEntity<?> response = leagueController.updateLeague(1L, leagueDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(leagueMapper, times(1)).toEntity(leagueDTO);
        verify(leagueService, times(1)).updateLeague(1L, league);
        verify(leagueModelAssembler, times(1)).toModel(league);
    }

    @Test
    void getTeamsOfLeague() {
    }
}