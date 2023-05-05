package com.gkritas.footballsimrestapi.controller;

import com.gkritas.footballsimrestapi.model.Match;
import com.gkritas.footballsimrestapi.model.Season;
import com.gkritas.footballsimrestapi.service.MatchService;
import com.gkritas.footballsimrestapi.service.MatchSimulator;
import com.gkritas.footballsimrestapi.service.SeasonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/test")
public class TestController {

    private final SeasonService seasonService;
    private final MatchService matchService;
    private final MatchSimulator matchSimulator;

    public TestController(SeasonService seasonService, MatchService matchService, MatchSimulator matchSimulator) {
        this.seasonService = seasonService;
        this.matchService = matchService;
        this.matchSimulator = matchSimulator;
    }

    @GetMapping
    public ResponseEntity<?> testing() {
        Season season = seasonService.getSeasonById(1L);
        List<Match> matches = seasonService.generateMatches(season);
        for (Match match : matches) {
            matchSimulator.simulateMatch(match);
        }
        return ResponseEntity.noContent().build();
    }
}
