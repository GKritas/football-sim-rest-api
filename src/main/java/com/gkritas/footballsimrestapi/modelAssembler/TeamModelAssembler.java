package com.gkritas.footballsimrestapi.modelAssembler;

import com.gkritas.footballsimrestapi.DTO.TeamDTO;
import com.gkritas.footballsimrestapi.controller.TeamController;
import com.gkritas.footballsimrestapi.mapper.TeamMapper;
import com.gkritas.footballsimrestapi.model.Team;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@Component
public class TeamModelAssembler implements RepresentationModelAssembler<Team, EntityModel<TeamDTO>> {

    private final TeamMapper teamMapper;

    public TeamModelAssembler(TeamMapper teamMapper) {
        this.teamMapper = teamMapper;
    }

    @Override
    public EntityModel<TeamDTO> toModel(Team team) {
        TeamDTO teamDTO = teamMapper.toDTO(team);
        Link selfLink = linkTo(methodOn(TeamController.class).getSingleTeam(team.getId())).withSelfRel();
        Link allLink = linkTo(methodOn(TeamController.class).getAllTeams()).withRel("teams");

        return EntityModel.of(teamDTO, selfLink, allLink);
    }
}
