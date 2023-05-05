package com.gkritas.footballsimrestapi.controller;

import com.gkritas.footballsimrestapi.DTO.SaveGameDTO;
import com.gkritas.footballsimrestapi.mapper.SaveGameMapper;
import com.gkritas.footballsimrestapi.model.SaveGame;
import com.gkritas.footballsimrestapi.modelAssembler.SaveGameModelAssembler;
import com.gkritas.footballsimrestapi.service.SaveGameService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/save-games")
public class SaveGameController {

    private final SaveGameService saveGameService;
    private final SaveGameModelAssembler saveGameModelAssembler;
    private final SaveGameMapper saveGameMapper;

    public SaveGameController(SaveGameService saveGameService,
                              SaveGameModelAssembler saveGameModelAssembler,
                              SaveGameMapper saveGameMapper) {
        this.saveGameService = saveGameService;
        this.saveGameModelAssembler = saveGameModelAssembler;
        this.saveGameMapper = saveGameMapper;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<SaveGameDTO>>> getAllSaveGames() {
        List<SaveGame> saveGames = saveGameService.findAllSaveGames();
        List<EntityModel<SaveGameDTO>> saveGameModels = saveGames.stream().map(saveGameModelAssembler::toModel).toList();
        return ResponseEntity.ok(CollectionModel.of(saveGameModels));
    }

    @GetMapping("{id}")
    public ResponseEntity<EntityModel<SaveGameDTO>> getSingleSaveGame(@PathVariable Long id) {
        SaveGame saveGame = saveGameService.findSaveGameById(id);
        EntityModel<SaveGameDTO> saveGameModel = saveGameModelAssembler.toModel(saveGame);
        return ResponseEntity.ok(saveGameModel);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteSaveGame(@PathVariable Long id) {
        saveGameService.deleteSaveGameById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<EntityModel<SaveGameDTO>> createSaveGame(@RequestBody SaveGameDTO dto) {
        dto.setCreatedAt(LocalDateTime.now());
        dto.setUpdatedAt(LocalDateTime.now());
        SaveGame saveGame = saveGameMapper.toEntity(dto);
        SaveGame createdSaveGame = saveGameService.createSaveGame(saveGame);
        EntityModel<SaveGameDTO> saveGameModel = saveGameModelAssembler.toModel(createdSaveGame);
        return ResponseEntity.created(saveGameModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<EntityModel<SaveGameDTO>> updateSaveGame(@PathVariable Long id, @RequestBody SaveGameDTO dto) {
        dto.setUpdatedAt(LocalDateTime.now());
        SaveGame saveGame = saveGameMapper.toEntity(dto);
        SaveGame updatedSaveGame = saveGameService.updateSaveGame(id, saveGame);
        EntityModel<SaveGameDTO> saveGameModel = saveGameModelAssembler.toModel(updatedSaveGame);
        return ResponseEntity.created(saveGameModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(saveGameModel);
    }
}
