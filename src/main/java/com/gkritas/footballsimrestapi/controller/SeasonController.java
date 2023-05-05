package com.gkritas.footballsimrestapi.controller;

import com.gkritas.footballsimrestapi.DTO.SeasonDTO;
import com.gkritas.footballsimrestapi.mapper.SeasonMapper;
import com.gkritas.footballsimrestapi.model.Season;
import com.gkritas.footballsimrestapi.modelAssembler.SeasonModelAssembler;
import com.gkritas.footballsimrestapi.service.SeasonService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/seasons")
public class SeasonController {

    private final SeasonService seasonService;
    private final SeasonModelAssembler seasonModelAssembler;
    private final SeasonMapper seasonMapper;

    public SeasonController(SeasonService seasonService, SeasonModelAssembler seasonModelAssembler, SeasonMapper seasonMapper) {
        this.seasonService = seasonService;
        this.seasonModelAssembler = seasonModelAssembler;
        this.seasonMapper = seasonMapper;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<SeasonDTO>>> getAllSeasons() {
        List<Season> seasons = seasonService.getAllSeasons();
        List<EntityModel<SeasonDTO>> seasonModels = seasons.stream().map(seasonModelAssembler::toModel).toList();

        return ResponseEntity.ok(CollectionModel.of(seasonModels));
    }

    @GetMapping("{id}")
    public ResponseEntity<EntityModel<SeasonDTO>> getSingleSeason(@PathVariable Long id) {
        Season season = seasonService.getSeasonById(id);
        EntityModel<SeasonDTO> seasonModel = seasonModelAssembler.toModel(season);
        return ResponseEntity.ok(seasonModel);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteSeason(@PathVariable Long id) {
        seasonService.deleteSeason(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<EntityModel<SeasonDTO>> createSeason(@RequestBody SeasonDTO dto) {
        Season season = seasonMapper.toEntity(dto);
        Season createdSeason = seasonService.createSeason(season);
        EntityModel<SeasonDTO> seasonModel = seasonModelAssembler.toModel(createdSeason);
        return ResponseEntity.created(seasonModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(seasonModel);
    }

    @PutMapping("{id}")
    public ResponseEntity<EntityModel<SeasonDTO>> updateSeason(@PathVariable Long id, @RequestBody SeasonDTO dto) {
        Season season = seasonMapper.toEntity(dto);
        Season updatedSeason = seasonService.updateSeason(id, season);
        EntityModel<SeasonDTO> seasonModel = seasonModelAssembler.toModel(updatedSeason);
        return ResponseEntity.created(seasonModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(seasonModel);
    }
}
