package com.gkritas.footballsimrestapi.service;

import com.gkritas.footballsimrestapi.exception.SaveGameNotFoundException;
import com.gkritas.footballsimrestapi.model.SaveGame;
import com.gkritas.footballsimrestapi.repository.SaveGameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaveGameServiceTest {

    @Mock
    private SaveGameRepository saveGameRepository;

    @InjectMocks
    private SaveGameService saveGameService;

    private SaveGame saveGame1;
    private SaveGame saveGame2;

    @BeforeEach
    void setUp() {
        saveGame1 = new SaveGame();
        saveGame1.setId(1L);
        saveGame1.setName("SaveGame 1");
        saveGame1.setCreatedAt(LocalDateTime.now());
        saveGame1.setUpdatedAt(LocalDateTime.now());

        saveGame2 = new SaveGame();
        saveGame2.setId(2L);
        saveGame2.setName("SaveGame 2");
        saveGame2.setCreatedAt(LocalDateTime.now());
        saveGame2.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void findAllSaveGames() {
        when(saveGameRepository.findAll()).thenReturn(List.of(saveGame1, saveGame2));

        List<SaveGame> result = saveGameService.findAllSaveGames();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(saveGameRepository, times(1)).findAll();
    }

    @Test
    void findSaveGameById_found() {
        when(saveGameRepository.findById(1L)).thenReturn(Optional.of(saveGame1));

        SaveGame result = saveGameService.findSaveGameById(1L);

        assertNotNull(result);
        assertEquals(saveGame1, result);
        verify(saveGameRepository, times(1)).findById(1L);
    }

    @Test
    void findSaveGameById_notFound() {
        when(saveGameRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(SaveGameNotFoundException.class, () -> saveGameService.findSaveGameById(1L));

        verify(saveGameRepository, times(1)).findById(1L);
    }
    @Test
    void deleteSaveGameById() {
        when(saveGameRepository.findById(1L)).thenReturn(Optional.of(saveGame1));

        saveGameService.deleteSaveGameById(1L);

        verify(saveGameRepository, times(1)).findById(1L);
        verify(saveGameRepository, times(1)).delete(saveGame1);
    }

    @Test
    void createSaveGame() {
        when(saveGameRepository.save(saveGame1)).thenReturn(saveGame1);

        SaveGame result = saveGameService.createSaveGame(saveGame1);

        assertEquals(saveGame1, result);
        verify(saveGameRepository, times(1)).save(saveGame1);
    }

    @Test
    void updateSaveGame() {
        SaveGame updatedSaveGame = saveGame1;
        updatedSaveGame.setName("Updated Save Game");

        when(saveGameRepository.findById(1L)).thenReturn(Optional.of(saveGame1));
        when(saveGameRepository.save(any(SaveGame.class))).thenReturn(updatedSaveGame);

        SaveGame result = saveGameService.updateSaveGame(1L, updatedSaveGame);

        assertEquals(updatedSaveGame.getName(), result.getName());
        assertEquals(updatedSaveGame.getLeagues(), result.getLeagues());
        verify(saveGameRepository, times(1)).findById(1L);
        verify(saveGameRepository, times(1)).save(any(SaveGame.class));
    }
}