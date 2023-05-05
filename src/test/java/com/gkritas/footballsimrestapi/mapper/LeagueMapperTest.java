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

    @InjectMocks
    private LeagueMapper leagueMapper;

    private League league;
    private LeagueDTO leagueDTO;
    private SaveGame saveGame;
    @BeforeEach
    void setUp() {
        saveGame = new SaveGame();
        saveGame.setId(1L);

        league = new League();
        league.setId(1L);
        league.setName("Test League");
        league.setSaveGame(saveGame);

        leagueDTO = new LeagueDTO();
        leagueDTO.setId(1L);
        leagueDTO.setName("Test League");
        leagueDTO.setSaveGameId(1L);
    }

    @Test
    void toDTO() {
        LeagueDTO result = leagueMapper.toDTO(league);

        assertEquals(leagueDTO.getId(), result.getId());
        assertEquals(leagueDTO.getName(), result.getName());
        assertEquals(leagueDTO.getSaveGameId(), result.getSaveGameId());
    }

    @Test
    void toEntity() {
        when(saveGameService.findSaveGameById(1L)).thenReturn(saveGame);

        League result = leagueMapper.toEntity(leagueDTO);

        assertEquals(league.getId(), result.getId());
        assertEquals(league.getName(), result.getName());
        assertEquals(league.getSaveGame().getId(), result.getSaveGame().getId());
    }
}