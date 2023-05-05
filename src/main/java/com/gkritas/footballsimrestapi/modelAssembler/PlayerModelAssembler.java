package com.gkritas.footballsimrestapi.modelAssembler;

import com.gkritas.footballsimrestapi.DTO.PlayerDTO;
import com.gkritas.footballsimrestapi.controller.PlayerController;
import com.gkritas.footballsimrestapi.mapper.PlayerMapper;
import com.gkritas.footballsimrestapi.model.Player;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PlayerModelAssembler implements RepresentationModelAssembler<Player, EntityModel<PlayerDTO>> {

    private final PlayerMapper playerMapper;

    public PlayerModelAssembler(PlayerMapper playerMapper) {
        this.playerMapper = playerMapper;
    }

    @Override
    public EntityModel<PlayerDTO> toModel(Player player) {
        PlayerDTO playerDTO = playerMapper.toDTO(player);
        Link selfLink = linkTo(methodOn(PlayerController.class).getSinglePlayer(player.getId())).withSelfRel();
        Link allLink = linkTo(methodOn(PlayerController.class).getAllPlayers()).withRel("players");

        return EntityModel.of(playerDTO, selfLink, allLink);
    }
}
