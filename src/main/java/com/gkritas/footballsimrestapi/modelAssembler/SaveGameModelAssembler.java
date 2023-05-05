package com.gkritas.footballsimrestapi.modelAssembler;

import com.gkritas.footballsimrestapi.DTO.SaveGameDTO;
import com.gkritas.footballsimrestapi.controller.SaveGameController;
import com.gkritas.footballsimrestapi.mapper.SaveGameMapper;
import com.gkritas.footballsimrestapi.model.SaveGame;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SaveGameModelAssembler implements RepresentationModelAssembler<SaveGame, EntityModel<SaveGameDTO>> {

    private final SaveGameMapper saveGameMapper;

    public SaveGameModelAssembler(SaveGameMapper saveGameMapper) {
        this.saveGameMapper = saveGameMapper;
    }

    @Override
    public EntityModel<SaveGameDTO> toModel(SaveGame saveGame) {
        SaveGameDTO saveGameDTO = saveGameMapper.toDTO(saveGame);
        Link selfLink = linkTo(methodOn(SaveGameController.class).getSingleSaveGame(saveGame.getId())).withSelfRel();
        Link allLink = linkTo(methodOn(SaveGameController.class).getAllSaveGames()).withRel("save-games");

        return EntityModel.of(saveGameDTO, selfLink, allLink);
    }
}
