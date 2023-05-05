package com.gkritas.footballsimrestapi.controller;

import com.gkritas.footballsimrestapi.DTO.LeagueDTO;
import com.gkritas.footballsimrestapi.DTO.TeamDTO;
import com.gkritas.footballsimrestapi.mapper.LeagueMapper;
import com.gkritas.footballsimrestapi.model.League;
import com.gkritas.footballsimrestapi.model.Team;
import com.gkritas.footballsimrestapi.modelAssembler.LeagueModelAssembler;
import com.gkritas.footballsimrestapi.modelAssembler.TeamModelAssembler;
import com.gkritas.footballsimrestapi.service.LeagueService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/leagues")
public class LeagueController {

    private final LeagueService leagueService;
    private final LeagueModelAssembler leagueModelAssembler;
    private final LeagueMapper leagueMapper;
    private final TeamModelAssembler teamModelAssembler;
    public LeagueController(LeagueService leagueService,
                            LeagueModelAssembler leagueModelAssembler,
                            LeagueMapper leagueMapper,
                            TeamModelAssembler teamModelAssembler) {
        this.leagueService = leagueService;
        this.leagueModelAssembler = leagueModelAssembler;
        this.leagueMapper = leagueMapper;
        this.teamModelAssembler = teamModelAssembler;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<LeagueDTO>>> getAllLeagues() {
        List<League> leagues = leagueService.getAllLeagues();
        List<EntityModel<LeagueDTO>> leagueModels = leagues.stream().map(leagueModelAssembler::toModel).toList();

        return ResponseEntity.ok(CollectionModel.of(leagueModels));
    }

    @GetMapping("{id}")
    public ResponseEntity<EntityModel<LeagueDTO>> getSingleLeague(@PathVariable Long id) {
        League league = leagueService.getLeagueById(id);
        EntityModel<LeagueDTO> leagueModel = leagueModelAssembler.toModel(league);
        return ResponseEntity.ok(leagueModel);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteLeague(@PathVariable Long id) {
        leagueService.deleteLeague(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<EntityModel<LeagueDTO>> createLeague(@RequestBody LeagueDTO dto) {
        League league = leagueMapper.toEntity(dto);
        League createdLeague = leagueService.createLeague(league);
        EntityModel<LeagueDTO> leagueModel = leagueModelAssembler.toModel(createdLeague);
        return ResponseEntity.created(leagueModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(leagueModel);
    }

    @PutMapping("{id}")
    public ResponseEntity<EntityModel<LeagueDTO>> updateLeague(@PathVariable Long id, @RequestBody LeagueDTO dto) {
        League league = leagueMapper.toEntity(dto);
        League updatedLeague = leagueService.updateLeague(id, league);
        EntityModel<LeagueDTO> leagueModel = leagueModelAssembler.toModel(updatedLeague);
        return ResponseEntity.created(leagueModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(leagueModel);
    }
    @GetMapping("{id}/teams")
    public ResponseEntity<CollectionModel<EntityModel<TeamDTO>>> getTeamsOfLeague(@PathVariable Long id) {
        League league = leagueService.getLeagueById(id);
        List<Team> teams = league.getTeams();
        List<EntityModel<TeamDTO>> teamModels = teams.stream().map(teamModelAssembler::toModel).toList();
        return ResponseEntity.ok(CollectionModel.of(teamModels));
    }

//    @PostMapping("{id}/draw")
//    public ResponseEntity<CollectionModel<EntityModel<MatchDTO>>> drawMatches(@PathVariable Long id) {
//        League league = leagueService.getLeagueById(id);
//        List<Match> matches = leagueService.generateMatches(league);
//        List<EntityModel<MatchDTO>> matchModels = matches.stream().map(matchModelAssembler::toModel).toList();
//        return ResponseEntity.ok(CollectionModel.of(matchModels));
//    }
//
//    @PutMapping("{id}/simulate")
//    public ResponseEntity<CollectionModel<EntityModel<MatchDTO>>> simulateLeague(@PathVariable Long id) {
//        League league = leagueService.getLeagueById(id);
//        List<Match> matches = leagueService.simulateMatches(league);
//        List<EntityModel<MatchDTO>> matchModels = matches.stream().map(matchModelAssembler::toModel).toList();
//        return ResponseEntity.ok(CollectionModel.of(matchModels));
//    }


}
