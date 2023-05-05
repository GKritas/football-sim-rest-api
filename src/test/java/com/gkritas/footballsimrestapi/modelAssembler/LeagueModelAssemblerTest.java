package com.gkritas.footballsimrestapi.modelAssembler;

import com.gkritas.footballsimrestapi.DTO.LeagueDTO;
import com.gkritas.footballsimrestapi.mapper.LeagueMapper;
import com.gkritas.footballsimrestapi.model.League;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.EntityModel;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LeagueModelAssemblerTest {

    @Mock
    private LeagueMapper leagueMapper;

    @InjectMocks
    private LeagueModelAssembler leagueModelAssembler;

    private League league;
    private LeagueDTO leagueDTO;

    @BeforeEach
    void setUp() {
        league = new League();
        league.setId(1L);
        league.setName("Test League");

        leagueDTO = new LeagueDTO();
        leagueDTO.setId(1L);
        leagueDTO.setName("Test League");
    }

    @Test
    void toModel() {
        when(leagueMapper.toDTO(any(League.class))).thenReturn(leagueDTO);

        EntityModel<LeagueDTO> entityModel = leagueModelAssembler.toModel(league);

        assertEquals(leagueDTO, entityModel.getContent());
        assertEquals(2, entityModel.getLinks().toList().size());
        assertEquals("self", entityModel.getLinks().toList().get(0).getRel().value());
        assertEquals("leagues", entityModel.getLinks().toList().get(1).getRel().value());
    }
}