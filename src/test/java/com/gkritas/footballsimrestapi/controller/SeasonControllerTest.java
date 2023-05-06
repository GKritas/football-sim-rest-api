package com.gkritas.footballsimrestapi.controller;

import com.gkritas.footballsimrestapi.DTO.SeasonDTO;
import com.gkritas.footballsimrestapi.mapper.SeasonMapper;
import com.gkritas.footballsimrestapi.model.Season;
import com.gkritas.footballsimrestapi.modelAssembler.SeasonModelAssembler;
import com.gkritas.footballsimrestapi.service.SeasonService;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@ExtendWith(MockitoExtension.class)
class SeasonControllerTest {

    @Mock
    private SeasonService seasonService;
    @Mock
    private SeasonModelAssembler seasonModelAssembler;
    @Mock
    private SeasonMapper seasonMapper;

    @InjectMocks
    private SeasonController seasonController;

    private Season season;
    private SeasonDTO seasonDTO;
    private EntityModel<SeasonDTO> seasonModel;
    private List<Season> seasons;

    @BeforeEach
    void setUp() {
        season = new Season();
        season.setId(1L);
        season.setYear(2023);

        seasonDTO = new SeasonDTO();
        seasonDTO.setId(1L);
        seasonDTO.setYear(2023);

        Link selfSeasonLink = linkTo(methodOn(SeasonController.class).getSingleSeason(1L)).withSelfRel();
        Link allSeasonsLink = linkTo(methodOn(SeasonController.class).getAllSeasons()).withRel("seasons");
        seasonModel = EntityModel.of(seasonDTO, selfSeasonLink, allSeasonsLink);
        seasons = List.of(season);
    }
    @Test
    void getAllSeasons() {
        when(seasonService.getAllSeasons()).thenReturn(seasons);
        when(seasonModelAssembler.toModel(season)).thenReturn(seasonModel);

        ResponseEntity<?> response = seasonController.getAllSeasons();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(seasonService, times(1)).getAllSeasons();
        verify(seasonModelAssembler, times(seasons.size())).toModel(any(Season.class));
    }

    @Test
    void getSingleSeason() {
        when(seasonService.getSeasonById(1L)).thenReturn(season);
        when(seasonModelAssembler.toModel(season)).thenReturn(seasonModel);

        ResponseEntity<?> response = seasonController.getSingleSeason(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(seasonService, times(1)).getSeasonById(1L);
        verify(seasonModelAssembler, times(1)).toModel(season);
    }

    @Test
    void deleteSeason() {
        doNothing().when(seasonService).deleteSeason(1L);

        ResponseEntity<?> response = seasonController.deleteSeason(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(seasonService, times(1)).deleteSeason(1L);
    }

    @Test
    void createSeason() {
        when(seasonMapper.toEntity(seasonDTO)).thenReturn(season);
        when(seasonService.createSeason(season)).thenReturn(season);
        when(seasonModelAssembler.toModel(season)).thenReturn(seasonModel);

        ResponseEntity<?> response = seasonController.createSeason(seasonDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(seasonMapper, times(1)).toEntity(seasonDTO);
        verify(seasonService, times(1)).createSeason(season);
        verify(seasonModelAssembler, times(1)).toModel(season);
    }

    @Test
    void updateSeason() {
        when(seasonMapper.toEntity(seasonDTO)).thenReturn(season);
        when(seasonService.updateSeason(1L, season)).thenReturn(season);
        when(seasonModelAssembler.toModel(season)).thenReturn(seasonModel);

        ResponseEntity<?> response = seasonController.updateSeason(1L, seasonDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(seasonMapper, times(1)).toEntity(seasonDTO);
        verify(seasonService, times(1)).updateSeason(1L, season);
        verify(seasonModelAssembler, times(1)).toModel(season);
    }
}