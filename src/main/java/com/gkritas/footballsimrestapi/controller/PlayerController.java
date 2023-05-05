package com.gkritas.footballsimrestapi.controller;

import com.gkritas.footballsimrestapi.DTO.PlayerDTO;
import com.gkritas.footballsimrestapi.mapper.PlayerMapper;
import com.gkritas.footballsimrestapi.model.Player;
import com.gkritas.footballsimrestapi.modelAssembler.PlayerModelAssembler;
import com.gkritas.footballsimrestapi.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/players")
public class PlayerController {
    private final PlayerService playerService;
    private final PlayerModelAssembler playerModelAssembler;
    private final PlayerMapper playerMapper;

    @Autowired
    public PlayerController(PlayerService playerService, PlayerModelAssembler playerModelAssembler, PlayerMapper playerMapper) {
        this.playerService = playerService;
        this.playerModelAssembler = playerModelAssembler;
        this.playerMapper = playerMapper;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<PlayerDTO>>> getAllPlayers() {
        List<Player> players = playerService.getAllPlayers();
        List<EntityModel<PlayerDTO>> playerModels = players
                .stream()
                .map(playerModelAssembler::toModel).toList();
        return ResponseEntity.ok(CollectionModel.of(playerModels));
    }

    @GetMapping("{id}")
    public ResponseEntity<EntityModel<PlayerDTO>> getSinglePlayer(@PathVariable Long id) {
        Player player = playerService.getPlayerById(id);
        EntityModel<PlayerDTO> playerModel = playerModelAssembler.toModel(player);
        return ResponseEntity.ok(playerModel);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletePlayer(@PathVariable Long id) {
        playerService.deletePlayerById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<EntityModel<PlayerDTO>> createPlayer(@RequestBody PlayerDTO dto) {
        Player player = playerMapper.toEntity(dto);
        Player createdPlayer = playerService.createdPlayer(player);
        EntityModel<PlayerDTO> playerModel = playerModelAssembler.toModel(createdPlayer);
        return ResponseEntity.created(playerModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(playerModel);
    }

    @PutMapping("{id}")
    public ResponseEntity<EntityModel<PlayerDTO>> updatePlayer(@PathVariable Long id, @RequestBody PlayerDTO dto) {
        Player player = playerMapper.toEntity(dto);
        Player updatedPlayer = playerService.updatedPlayer(id, player);
        EntityModel<PlayerDTO> playerModel = playerModelAssembler.toModel(updatedPlayer);
        return ResponseEntity.created(playerModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(playerModel);
    }
}
