package com.gkritas.footballsimrestapi.mapper;

import com.gkritas.footballsimrestapi.DTO.LeagueDTO;
import com.gkritas.footballsimrestapi.model.League;
import com.gkritas.footballsimrestapi.model.SaveGame;
import com.gkritas.footballsimrestapi.service.SaveGameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LeagueMapperTest {

    @Mock
    private SaveGameService saveGameService;
    @Mock
    private SeasonMapper seasonMapper;
    @Mock
    private TeamMapper teamMapper;

    @InjectMocks
    private LeagueMapper leagueMapper;

    private League league;
    private LeagueDTO dto;
    private SaveGame saveGame;
    @BeforeEach
    void setUp() {
        league = new League();
        league.setId(1L);
        league.setName("Test League");

        dto = new LeagueDTO();
        dto.setId(1L);
        dto.setName("Test League");
        dto.setSaveGameId(1L);

        saveGame = new SaveGame();
        saveGame.setId(1L);
        league.setSaveGame(saveGame);
    }

    @Test
    void toDTO() {
        LeagueDTO result = leagueMapper.toDTO(league);

        assertEquals(1L, dto.getId());
        assertEquals("Test League", dto.getName());
        assertEquals(1L, dto.getSaveGameId());
    }

    @Test
    void toEntity() {
        when(saveGameService.findSaveGameById(1L)).thenReturn(saveGame);

        League result = leagueMapper.toEntity(dto);

        assertEquals(1L, league.getId());
        assertEquals("Test League", league.getName());
        assertEquals(1L, league.getSaveGame().getId());
    }
}