package com.gkritas.footballsimrestapi.controller;

import com.gkritas.footballsimrestapi.DTO.MatchDTO;
import com.gkritas.footballsimrestapi.mapper.MatchMapper;
import com.gkritas.footballsimrestapi.model.Match;
import com.gkritas.footballsimrestapi.modelAssembler.MatchModelAssembler;
import com.gkritas.footballsimrestapi.service.MatchService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/matches")
public class MatchController {

    private final MatchService matchService;

    private final MatchModelAssembler matchModelAssembler;
    private final MatchMapper matchMapper;

    public MatchController(MatchService matchService, MatchModelAssembler modelAssembler, MatchMapper matchMapper) {
        this.matchService = matchService;
        this.matchModelAssembler = modelAssembler;
        this.matchMapper = matchMapper;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<MatchDTO>>> getAllMatches() {
        List<Match> matches = matchService.getAllMatches();
        List<EntityModel<MatchDTO>> matchModels = matches.stream().map(matchModelAssembler::toModel).toList();

        return ResponseEntity.ok(CollectionModel.of(matchModels));
    }

    @GetMapping("{id}")
    public ResponseEntity<EntityModel<MatchDTO>> getSingleMatch(@PathVariable Long id) {
        Match match = matchService.getMatchById(id);
        EntityModel<MatchDTO> matchModel = matchModelAssembler.toModel(match);

        return ResponseEntity.ok(matchModel);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteMatch(@PathVariable Long id) {
        matchService.deleteMatch(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<EntityModel<MatchDTO>> createMatch(@RequestBody MatchDTO dto) {
        Match match = matchMapper.toEntity(dto);
        Match createdMatch = matchService.createMatch(match);
        EntityModel<MatchDTO> matchModel = matchModelAssembler.toModel(createdMatch);
        return ResponseEntity.created(matchModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(matchModel);
    }

    @PutMapping("{id}")
    public ResponseEntity<EntityModel<MatchDTO>> updatedMatch(@PathVariable Long id, @RequestBody MatchDTO dto) {
        Match match = matchMapper.toEntity(dto);
        Match updatedMatch = matchService.updateMatch(id, match);
        EntityModel<MatchDTO> matchModel = matchModelAssembler.toModel(updatedMatch);
        return ResponseEntity.created(matchModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(matchModel);
    }

}
