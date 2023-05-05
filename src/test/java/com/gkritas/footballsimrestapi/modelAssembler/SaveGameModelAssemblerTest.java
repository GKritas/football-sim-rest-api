package com.gkritas.footballsimrestapi.modelAssembler;

import com.gkritas.footballsimrestapi.DTO.LeagueDTO;
import com.gkritas.footballsimrestapi.DTO.SaveGameDTO;
import com.gkritas.footballsimrestapi.controller.SaveGameController;
import com.gkritas.footballsimrestapi.mapper.SaveGameMapper;
import com.gkritas.footballsimrestapi.model.League;
import com.gkritas.footballsimrestapi.model.SaveGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.EntityModel;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@ExtendWith(MockitoExtension.class)
class SaveGameModelAssemblerTest {
    @Mock
    private SaveGameMapper saveGameMapper;

    @InjectMocks
    private SaveGameModelAssembler saveGameModelAssembler;

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

        lenient().when(saveGameMapper.toDTO(saveGame)).thenReturn(saveGameDTO);
    }

    @Test
    void toModel() {
        EntityModel<SaveGameDTO> result = saveGameModelAssembler.toModel(saveGame);

        assertEquals(saveGameDTO, result.getContent());
        assertEquals(2, result.getLinks().toList().size());
        assertEquals(linkTo(methodOn(SaveGameController.class).getSingleSaveGame(saveGame.getId())).withSelfRel(),
                result.getLink("self").orElseThrow(() -> new AssertionError("Self link not found")));
        assertEquals(linkTo(methodOn(SaveGameController.class).getAllSaveGames()).withRel("save-games"),
                result.getLink("save-games").orElseThrow(() -> new AssertionError("Save-games link not found")));
    }

    @Test
    void toModelWithNullSaveGameProperties() {
        SaveGame saveGameWithNullProperties = new SaveGame();
        SaveGameDTO saveGameDTOWithNullProperties = new SaveGameDTO();

        when(saveGameMapper.toDTO(saveGameWithNullProperties)).thenReturn(saveGameDTOWithNullProperties);

        EntityModel<SaveGameDTO> result = saveGameModelAssembler.toModel(saveGameWithNullProperties);

        assertEquals(saveGameDTOWithNullProperties, result.getContent());
        assertEquals(2, result.getLinks().toList().size());
    }

    @Test
    void toModelWithMapperReturningNull() {
        SaveGame saveGameWithNullProperties = new SaveGame();

        when(saveGameMapper.toDTO(saveGameWithNullProperties)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> saveGameModelAssembler.toModel(saveGameWithNullProperties));
    }
}