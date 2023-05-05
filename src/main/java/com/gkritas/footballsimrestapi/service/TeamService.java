package com.gkritas.footballsimrestapi.service;

import com.gkritas.footballsimrestapi.exception.TeamNotFoundException;
import com.gkritas.footballsimrestapi.model.Team;
import com.gkritas.footballsimrestapi.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Team getTeamById(Long id) {
        return teamRepository.findById(id).orElseThrow(() -> new TeamNotFoundException("Team not found with id: " + id));
    }

    public void deleteTeamById(Long id) {
        Team team = getTeamById(id);
        teamRepository.delete(team);
    }

    public Team createTeam(Team team) {
        return teamRepository.save(team);
    }

    public Team updateTeam(Long id, Team team) {
        Team updatedTeam = getTeamById(id);
        updatedTeam.setName(team.getName());
        updatedTeam.setDefence(team.getDefence());
        updatedTeam.setMidfield(team.getMidfield());
        updatedTeam.setAttack(team.getAttack());
        updatedTeam.setPoints(team.getPoints());
        updatedTeam.setGoalsScored(team.getGoalsScored());
        updatedTeam.setGoalsConceded(team.getGoalsConceded());
        updatedTeam.setWins(team.getWins());
        updatedTeam.setDraws(team.getDraws());
        updatedTeam.setLosses(team.getLosses());
        updatedTeam.setLeague(team.getLeague());
        updatedTeam.setPlayers(team.getPlayers());
        return teamRepository.save(updatedTeam);
    }
}
