package com.gkritas.footballsimrestapi.modelAssembler;

import com.gkritas.footballsimrestapi.DTO.MatchDTO;
import com.gkritas.footballsimrestapi.controller.MatchController;
import com.gkritas.footballsimrestapi.mapper.MatchMapper;
import com.gkritas.footballsimrestapi.model.Match;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MatchModelAssembler implements RepresentationModelAssembler<Match, EntityModel<MatchDTO>> {

    private final MatchMapper matchMapper;

    public MatchModelAssembler(MatchMapper matchMapper) {
        this.matchMapper = matchMapper;
    }

    @Override
    public EntityModel<MatchDTO> toModel(Match match) {
        MatchDTO matchDTO = matchMapper.toDTO(match);
        Link selfLink = linkTo(methodOn(MatchController.class).getSingleMatch(match.getId())).withSelfRel();
        Link allLink = linkTo(methodOn(MatchController.class).getAllMatches()).withRel("matches");

        return EntityModel.of(matchDTO, selfLink, allLink);
    }
}
