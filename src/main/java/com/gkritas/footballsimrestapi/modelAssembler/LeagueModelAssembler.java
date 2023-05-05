package com.gkritas.footballsimrestapi.modelAssembler;

import com.gkritas.footballsimrestapi.DTO.LeagueDTO;
import com.gkritas.footballsimrestapi.controller.LeagueController;
import com.gkritas.footballsimrestapi.mapper.LeagueMapper;
import com.gkritas.footballsimrestapi.model.League;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class LeagueModelAssembler implements RepresentationModelAssembler<League, EntityModel<LeagueDTO>> {
    private final LeagueMapper leagueMapper;

    public LeagueModelAssembler(LeagueMapper leagueMapper) {
        this.leagueMapper = leagueMapper;
    }

    @Override
    public EntityModel<LeagueDTO> toModel(League league) {
        LeagueDTO leagueDTO = leagueMapper.toDTO(league);
        Link selfLink = linkTo(methodOn(LeagueController.class).getSingleLeague(league.getId())).withSelfRel();
        Link allLink = linkTo(methodOn(LeagueController.class).getAllLeagues()).withRel("leagues");
        return EntityModel.of(leagueDTO, selfLink, allLink);
    }
}
