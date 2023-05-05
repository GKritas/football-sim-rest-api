package com.gkritas.footballsimrestapi.mapper;

import com.gkritas.footballsimrestapi.DTO.LeagueDTO;
import com.gkritas.footballsimrestapi.DTO.SaveGameDTO;
import com.gkritas.footballsimrestapi.model.League;
import com.gkritas.footballsimrestapi.model.SaveGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class SaveGameMapperTest {

    @Mock
    private LeagueMapper leagueMapper;

    @InjectMocks
    private SaveGameMapper saveGameMapper;

    private SaveGame saveGame;
    private SaveGameDTO saveGameDTO;

    @BeforeEach
    void setUp() {
        League league = new League();
        league.setId(1L);
        LeagueDTO leagueDTO = new LeagueDTO();
        leagueDTO.setId(1L);

        saveGame = new SaveGame();
        saveGame.setId(1L);
        saveGame.setName("Test save game");
        saveGame.setCreatedAt(LocalDateTime.now());
        saveGame.setUpdatedAt(LocalDateTime.now());
        saveGame.setLeagues(List.of(league));

        saveGameDTO = new SaveGameDTO();
        saveGameDTO.setId(1L);
        saveGameDTO.setName("Test save game");
        saveGameDTO.setCreatedAt(LocalDateTime.now());
        saveGameDTO.setUpdatedAt(LocalDateTime.now());
        saveGameDTO.setLeagues(List.of(leagueDTO));

        lenient().when(leagueMapper.toDTO(league)).thenReturn(leagueDTO);
        lenient().when(leagueMapper.toEntity(leagueDTO)).thenReturn(league);
    }

    @Test
    void toDTO() {
        SaveGameDTO result = saveGameMapper.toDTO(saveGame);

        assertEquals(saveGameDTO.getId(), result.getId());
        assertEquals(saveGameDTO.getName(), result.getName());
        assertEquals(saveGameDTO.getLeagues().size(), result.getLeagues().size());
    }

    @Test
    void toEntity() {
        SaveGame result = saveGameMapper.toEntity(saveGameDTO);

        assertEquals(saveGame.getId(), result.getId());
        assertEquals(saveGame.getName(), result.getName());
        assertEquals(saveGame.getLeagues().size(), result.getLeagues().size());
    }

    @Test
    void toDTO_withNullLeagues() {
        saveGame.setLeagues(null);
        SaveGameDTO result = saveGameMapper.toDTO(saveGame);

        assertEquals(saveGameDTO.getId(), result.getId());
        assertEquals(saveGameDTO.getName(), result.getName());
        assertEquals(0, result.getLeagues().size());
    }

    @Test
    void toEntity_withNullLeagues() {
        saveGameDTO.setLeagues(null);
        SaveGame result = saveGameMapper.toEntity(saveGameDTO);

        assertEquals(saveGame.getId(), result.getId());
        assertEquals(saveGame.getName(), result.getName());
        assertEquals(0, result.getLeagues().size());
    }
}