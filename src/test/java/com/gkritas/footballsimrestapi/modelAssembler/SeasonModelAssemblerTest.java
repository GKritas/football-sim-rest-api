package com.gkritas.footballsimrestapi.modelAssembler;

import com.gkritas.footballsimrestapi.DTO.SeasonDTO;
import com.gkritas.footballsimrestapi.mapper.SeasonMapper;
import com.gkritas.footballsimrestapi.model.Season;
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
class SeasonModelAssemblerTest {
    @Mock
    private SeasonMapper seasonMapper;

    @InjectMocks
    private SeasonModelAssembler seasonModelAssembler;

    private Season season;
    private SeasonDTO seasonDTO;

    @BeforeEach
    void setUp() {
        season = new Season();
        season.setId(1L);
        season.setYear(2023);

        seasonDTO = new SeasonDTO();
        seasonDTO.setId(1L);
        seasonDTO.setYear(2023);
    }

    @Test
    void toModel() {
        when(seasonMapper.toDTO(season)).thenReturn(seasonDTO);

        EntityModel<SeasonDTO> entityModel = seasonModelAssembler.toModel(season);

        assertEquals(seasonDTO, entityModel.getContent());
        assertEquals(2, entityModel.getLinks().toList().size());
        assertEquals("self", entityModel.getLinks().toList().get(0).getRel().value());
        assertEquals("seasons", entityModel.getLinks().toList().get(1).getRel().value());
    }
}