package com.gkritas.footballsimrestapi.controller;

import com.gkritas.footballsimrestapi.DTO.PlayerDTO;
import com.gkritas.footballsimrestapi.DTO.TeamDTO;
import com.gkritas.footballsimrestapi.mapper.TeamMapper;
import com.gkritas.footballsimrestapi.model.Player;
import com.gkritas.footballsimrestapi.model.Team;
import com.gkritas.footballsimrestapi.modelAssembler.PlayerModelAssembler;
import com.gkritas.footballsimrestapi.modelAssembler.TeamModelAssembler;
import com.gkritas.footballsimrestapi.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/teams")
public class TeamController {


    private final TeamService teamService;
    private final TeamModelAssembler teamModelAssembler;
    private final TeamMapper teamMapper;
    private final PlayerModelAssembler playerModelAssembler;

    @Autowired
    public TeamController(TeamService teamService, TeamModelAssembler teamModelAssembler, TeamMapper teamMapper, PlayerModelAssembler playerModelAssembler) {
        this.teamService = teamService;
        this.teamModelAssembler = teamModelAssembler;
        this.teamMapper = teamMapper;
        this.playerModelAssembler = playerModelAssembler;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<TeamDTO>>> getAllTeams() {
        List<Team> teams = teamService.getAllTeams();
        List<EntityModel<TeamDTO>> teamModels = teams.stream().map(teamModelAssembler::toModel).toList();
        return ResponseEntity.ok(CollectionModel.of(teamModels));
    }

    @GetMapping("{id}")
    public ResponseEntity<EntityModel<TeamDTO>> getSingleTeam(@PathVariable Long id) {
        Team team = teamService.getTeamById(id);
        EntityModel<TeamDTO> teamModel = teamModelAssembler.toModel(team);
        return ResponseEntity.ok(teamModel);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteTeam(@PathVariable Long id) {
        teamService.deleteTeamById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<EntityModel<TeamDTO>> createTeam(@RequestBody TeamDTO dto) {
        Team team = teamMapper.toEntity(dto);
        Team createdTeam = teamService.createTeam(team);
        EntityModel<TeamDTO> teamModel = teamModelAssembler.toModel(createdTeam);
        return ResponseEntity.created(teamModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(teamModel);
    }

    @PutMapping("{id}")
    public ResponseEntity<EntityModel<TeamDTO>> updateTeam(@PathVariable Long id, @RequestBody TeamDTO dto) {
        Team team = teamMapper.toEntity(dto);
        Team updatedTeam = teamService.updateTeam(id, team);
        EntityModel<TeamDTO> teamModel = teamModelAssembler.toModel(updatedTeam);
        return ResponseEntity.created(teamModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(teamModel);
    }

    @GetMapping("{id}/players")
    public ResponseEntity<CollectionModel<EntityModel<PlayerDTO>>> getPlayersOfTeam(@PathVariable Long id) {
        Team team = teamService.getTeamById(id);
        List<Player> players = team.getPlayers();
        List<EntityModel<PlayerDTO>> playerModel = players.stream().map(playerModelAssembler::toModel).toList();
        return ResponseEntity.ok(CollectionModel.of(playerModel));
    }
}
