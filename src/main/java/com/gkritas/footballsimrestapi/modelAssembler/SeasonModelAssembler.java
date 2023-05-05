package com.gkritas.footballsimrestapi.modelAssembler;

import com.gkritas.footballsimrestapi.DTO.SeasonDTO;
import com.gkritas.footballsimrestapi.controller.SeasonController;
import com.gkritas.footballsimrestapi.mapper.SeasonMapper;
import com.gkritas.footballsimrestapi.model.Season;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@Component
public class SeasonModelAssembler implements RepresentationModelAssembler<Season, EntityModel<SeasonDTO>> {

    private final SeasonMapper seasonMapper;

    public SeasonModelAssembler(SeasonMapper seasonMapper) {
        this.seasonMapper = seasonMapper;
    }

    @Override
    public EntityModel<SeasonDTO> toModel(Season season) {
        SeasonDTO seasonDTO = seasonMapper.toDTO(season);
        Link selfLink = linkTo(methodOn(SeasonController.class).getSingleSeason(season.getId())).withSelfRel();
        Link allLink = linkTo(methodOn(SeasonController.class).getAllSeasons()).withRel("seasons");
        return EntityModel.of(seasonDTO, selfLink, allLink);
    }
}
