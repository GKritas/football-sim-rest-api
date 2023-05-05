package com.gkritas.footballsimrestapi.service;

import com.gkritas.footballsimrestapi.exception.MatchNotFoundException;
import com.gkritas.footballsimrestapi.model.Match;
import com.gkritas.footballsimrestapi.model.Team;
import com.gkritas.footballsimrestapi.repository.MatchRepository;
import com.gkritas.footballsimrestapi.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class MatchService {

    private static final Random RANDOM = new Random();
    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public MatchService(MatchRepository matchRepository,
                        TeamRepository teamRepository) {
        this.matchRepository = matchRepository;
        this.teamRepository = teamRepository;
    }

    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

    public Match getMatchById(Long id) {
        return matchRepository
                .findById(id)
                .orElseThrow(() ->
                        new MatchNotFoundException("Match not found with id: " + id));
    }

    public List<Match> getLastFiveMatches(Team team) {
        Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "matchDate");
        return matchRepository.findLastFiveMatchesByTeam(team.getId(), pageable);
    }

    public void deleteMatch(Long id) {
        Match match = getMatchById(id);
        matchRepository.delete(match);
    }

    public Match createMatch(Match match) {
        return matchRepository.save(match);
    }

    public Match updateMatch(Long id, Match match) {
        Match updatedMatch = getMatchById(id);
        updatedMatch.setSeason(match.getSeason());
        updatedMatch.setHomeTeam(match.getHomeTeam());
        updatedMatch.setAwayTeam(match.getAwayTeam());
        updatedMatch.setHomeTeamGoals(match.getHomeTeamGoals());
        updatedMatch.setAwayTeamGoals(match.getAwayTeamGoals());
        updatedMatch.setMatchDate(match.getMatchDate());

        return matchRepository.save(updatedMatch);
    }

}
