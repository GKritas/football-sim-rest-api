package com.gkritas.footballsimrestapi.service;

import com.gkritas.footballsimrestapi.exception.LeagueNotFoundException;
import com.gkritas.footballsimrestapi.model.League;
import com.gkritas.footballsimrestapi.model.Match;
import com.gkritas.footballsimrestapi.model.Team;
import com.gkritas.footballsimrestapi.repository.LeagueRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class LeagueService {

    private final LeagueRepository leagueRepository;
    private final MatchService matchService;

    public LeagueService(LeagueRepository leagueRepository, MatchService matchService) {
        this.leagueRepository = leagueRepository;
        this.matchService = matchService;
    }

    public List<League> getAllLeagues() {
        return leagueRepository.findAll();
    }

    public League getLeagueById(Long id) {
        return leagueRepository
                .findById(id)
                .orElseThrow(() ->
                        new LeagueNotFoundException("League not found with id: " + id));

    }

    public void deleteLeague(Long id) {
        League deletedLeague = getLeagueById(id);
        leagueRepository.delete(deletedLeague);
    }

    public League createLeague(League league) {
        return leagueRepository.save(league);
    }

    public League updateLeague(Long id, League league) {
        League updatedLeague = getLeagueById(id);
        updatedLeague.setName(league.getName());
        updatedLeague.setTeams(league.getTeams());
        updatedLeague.setSeasons(league.getSeasons());

        return leagueRepository.save(updatedLeague);
    }

}
