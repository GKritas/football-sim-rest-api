package com.gkritas.footballsimrestapi.service;

import com.gkritas.footballsimrestapi.exception.SeasonNotFoundException;
import com.gkritas.footballsimrestapi.model.Match;
import com.gkritas.footballsimrestapi.model.Season;
import com.gkritas.footballsimrestapi.model.Team;
import com.gkritas.footballsimrestapi.repository.SeasonRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class SeasonService {

    private final SeasonRepository seasonRepository;
    private final MatchService matchService;

    public SeasonService(SeasonRepository seasonRepository, MatchService matchService) {
        this.seasonRepository = seasonRepository;
        this.matchService = matchService;
    }

    public List<Season> getAllSeasons() {
        return seasonRepository.findAll();
    }

    public Season getSeasonById(Long id) {
        return seasonRepository
                .findById(id)
                .orElseThrow(() ->
                        new SeasonNotFoundException("Season not found with id: " + id));
    }

    public void deleteSeason(Long id) {
        Season season = getSeasonById(id);
        seasonRepository.delete(season);
    }

    public Season createSeason(Season season) {
        return seasonRepository.save(season);
    }

    public Season updateSeason(Long id, Season season) {
        Season updatedSeason = getSeasonById(id);
        updatedSeason.setYear(season.getYear());
        updatedSeason.setStartDate(season.getStartDate());
        updatedSeason.setEndDate(season.getEndDate());
        updatedSeason.setLeague(season.getLeague());
        updatedSeason.setMatches(season.getMatches());

        return seasonRepository.save(updatedSeason);
    }
    public List<Match> generateMatches(Season season) {
        List<Team> teams = season.getLeague().getTeams();
        Collections.shuffle(teams);
        List<Match> matches = new ArrayList<>();
        int numOfTeams = teams.size();
        int totalRounds = (numOfTeams - 1) * 2;
        int matchesPerRound = numOfTeams / 2;

        for (int round = 1; round <= totalRounds; round++) {
            for (int match = 1; match <= matchesPerRound; match++) {
                int homeTeamIndex = (round + match - 2) % (numOfTeams - 1);
                int awayTeamIndex = (numOfTeams - 1 + round - match) % (numOfTeams - 1);

                if (match == 1) {
                    awayTeamIndex = numOfTeams - 1;
                }

                Match fixture = new Match(season, teams.get(homeTeamIndex), teams.get(awayTeamIndex));
                matches.add(fixture);
                matchService.createMatch(fixture);
            }
        }
        return matches;
    }
}
