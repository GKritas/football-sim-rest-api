package com.gkritas.footballsimrestapi.modelAssembler;

import com.gkritas.footballsimrestapi.DTO.TeamDTO;
import com.gkritas.footballsimrestapi.mapper.TeamMapper;
import com.gkritas.footballsimrestapi.model.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.EntityModel;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeamModelAssemblerTest {

    @Mock
    private TeamMapper teamMapper;
    @InjectMocks
    private TeamModelAssembler teamModelAssembler;

    private Team team;
    private TeamDTO teamDTO;

    @BeforeEach
    void setUp() {
        team = new Team();
        team.setId(1L);
        team.setName("Test Team");

        teamDTO = new TeamDTO();
        teamDTO.setId(1L);
        teamDTO.setName("Test Team");
    }

    @Test
    void toModel() {
        when(teamMapper.toDTO(team)).thenReturn(teamDTO);

        EntityModel<TeamDTO> entityModel = teamModelAssembler.toModel(team);

        assertEquals(teamDTO, entityModel.getContent());
        assertEquals(2, entityModel.getLinks().toList().size());
        assertEquals("self", entityModel.getLinks().toList().get(0).getRel().value());
        assertEquals("teams", entityModel.getLinks().toList().get(1).getRel().value());
    }
}