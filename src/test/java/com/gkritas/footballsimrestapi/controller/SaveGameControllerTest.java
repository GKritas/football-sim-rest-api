package com.gkritas.footballsimrestapi.controller;

import com.gkritas.footballsimrestapi.DTO.SaveGameDTO;
import com.gkritas.footballsimrestapi.mapper.SaveGameMapper;
import com.gkritas.footballsimrestapi.model.SaveGame;
import com.gkritas.footballsimrestapi.modelAssembler.SaveGameModelAssembler;
import com.gkritas.footballsimrestapi.service.SaveGameService;
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
class SaveGameControllerTest {

    @Mock
    private SaveGameService saveGameService;
    @Mock
    private SaveGameModelAssembler saveGameModelAssembler;
    @Mock
    private SaveGameMapper saveGameMapper;

    @InjectMocks
    private SaveGameController saveGameController;

    private SaveGame saveGame;
    private SaveGameDTO saveGameDTO;
    private EntityModel<SaveGameDTO> saveGameModel;
    private List<SaveGame> saveGames;

    @BeforeEach
    void setUp() {
        saveGame = new SaveGame();
        saveGame.setId(1L);
        saveGame.setName("Test Save Game");

        saveGameDTO = new SaveGameDTO();
        saveGameDTO.setId(1L);
        saveGameDTO.setName("Test Save Game");

        Link selfLink = linkTo(methodOn(SaveGameController.class).getSingleSaveGame(saveGame.getId())).withSelfRel();
        Link allLink = linkTo(methodOn(SaveGameController.class).getAllSaveGames()).withRel("save-games");
        saveGameModel = EntityModel.of(saveGameDTO, selfLink, allLink);
        saveGames = List.of(saveGame);


    }

    @Test
    void getAllSaveGames() {
        when(saveGameService.findAllSaveGames()).thenReturn(saveGames);
        when(saveGameModelAssembler.toModel(saveGame)).thenReturn(saveGameModel);

        ResponseEntity<?> response = saveGameController.getAllSaveGames();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(saveGameService, times(1)).findAllSaveGames();
        verify(saveGameModelAssembler, times(saveGames.size())).toModel(any(SaveGame.class));
    }

    @Test
    void getSingleSaveGame() {
        when(saveGameService.findSaveGameById(1L)).thenReturn(saveGame);
        when(saveGameModelAssembler.toModel(saveGame)).thenReturn(saveGameModel);

        ResponseEntity<?> response = saveGameController.getSingleSaveGame(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(saveGameService, times(1)).findSaveGameById(1L);
        verify(saveGameModelAssembler, times(1)).toModel(saveGame);
    }

    @Test
    void deleteSaveGame() {
        doNothing().when(saveGameService).deleteSaveGameById(1L);

        ResponseEntity<?> response = saveGameController.deleteSaveGame(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(saveGameService, times(1)).deleteSaveGameById(1L);
    }

    @Test
    void createSaveGame() {
        when(saveGameMapper.toEntity(saveGameDTO)).thenReturn(saveGame);
        when(saveGameService.createSaveGame(saveGame)).thenReturn(saveGame);
        when(saveGameModelAssembler.toModel(saveGame)).thenReturn(saveGameModel);

        ResponseEntity<?> response = saveGameController.createSaveGame(saveGameDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(saveGameMapper, times(1)).toEntity(saveGameDTO);
        verify(saveGameService, times(1)).createSaveGame(saveGame);
        verify(saveGameModelAssembler, times(1)).toModel(saveGame);
    }

    @Test
    void updateSaveGame() {
        when(saveGameMapper.toEntity(saveGameDTO)).thenReturn(saveGame);
        when(saveGameService.updateSaveGame(1L, saveGame)).thenReturn(saveGame);
        when(saveGameModelAssembler.toModel(saveGame)).thenReturn(saveGameModel);

        ResponseEntity<?> response = saveGameController.updateSaveGame(1L, saveGameDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(saveGameMapper, times(1)).toEntity(saveGameDTO);
        verify(saveGameService, times(1)).updateSaveGame(1L, saveGame);
        verify(saveGameModelAssembler, times(1)).toModel(saveGame);
    }
}