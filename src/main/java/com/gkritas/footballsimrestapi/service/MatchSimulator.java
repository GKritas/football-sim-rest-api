package com.gkritas.footballsimrestapi.service;

import com.gkritas.footballsimrestapi.model.Match;
import com.gkritas.footballsimrestapi.model.Team;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class MatchSimulator {

    private static final Random RANDOM = new Random();
    private final TeamService teamService;
    private final MatchService matchService;

    public MatchSimulator( TeamService teamService, MatchService matchService) {
        this.teamService = teamService;
        this.matchService = matchService;
    }

    public Match simulateMatch(Match match) {
        Team homeTeam = match.getHomeTeam();
        Team awayTeam = match.getAwayTeam();

        updateMatchGoals(match, homeTeam, awayTeam);
        updateTeamStats(homeTeam, awayTeam, match);

        return matchService.createMatch(match);
    }

    private void updateMatchGoals(Match match, Team homeTeam, Team awayTeam) {
        double homeTeamScoreWeight = calculateScoreWeight(homeTeam, awayTeam, true);
        double awayTeamScoreWeight = calculateScoreWeight(awayTeam, homeTeam, false);

        int homeTeamGoals = generateGoalsUsingPoissonDistribution(homeTeamScoreWeight);
        int awayTeamGoals = generateGoalsUsingPoissonDistribution(awayTeamScoreWeight);
        match.setHomeTeamGoals(homeTeamGoals);
        match.setAwayTeamGoals(awayTeamGoals);
    }

    private void updateTeamStats(Team homeTeam, Team awayTeam, Match match) {
        int homeTeamGoals = match.getHomeTeamGoals();
        int awayTeamGoals = match.getAwayTeamGoals();

        homeTeam.setGoalsScored(homeTeam.getGoalsScored() + homeTeamGoals);
        homeTeam.setGoalsConceded(homeTeam.getGoalsConceded() + awayTeamGoals);

        awayTeam.setGoalsScored(awayTeam.getGoalsScored() + awayTeamGoals);
        awayTeam.setGoalsConceded(awayTeam.getGoalsConceded() + homeTeamGoals);

        updateMatchResults(homeTeam, awayTeam, homeTeamGoals, awayTeamGoals);

        teamService.createTeam(homeTeam);
        teamService.createTeam(awayTeam);
    }

    private void updateMatchResults(Team homeTeam, Team awayTeam, int homeTeamGoals, int awayTeamGoals) {
        if (homeTeamGoals > awayTeamGoals) {
            homeTeam.setWins(homeTeam.getWins() + 1);
            homeTeam.setPoints(homeTeam.getPoints() + 3);
            awayTeam.setLosses(awayTeam.getLosses() + 1);
        } else if (homeTeamGoals < awayTeamGoals) {
            awayTeam.setWins(awayTeam.getWins() + 1);
            awayTeam.setPoints(awayTeam.getPoints() + 3);
            homeTeam.setLosses(homeTeam.getLosses() + 1);
        } else {
            homeTeam.setDraws(homeTeam.getDraws() + 1);
            homeTeam.setPoints(homeTeam.getPoints() + 1);

            awayTeam.setDraws(awayTeam.getDraws() + 1);
            awayTeam.setPoints(awayTeam.getPoints() + 1);
        }
    }

    private double calculateScoreWeight(Team homeTeam, Team awayTeam, boolean isHomeTeam) {
        double midfieldWeight = homeTeam.getMidfield() / (double) awayTeam.getMidfield();
        double attackWeight = homeTeam.getAttack() / (double) awayTeam.getDefence();
        double homeAdvantageFactor = isHomeTeam ? 1.1 : 0.9;
        double formWeight = calculateFormWeight(homeTeam, awayTeam);

        return (midfieldWeight + attackWeight + formWeight) / 3 * homeAdvantageFactor;
    }

    private double calculateFormWeight(Team homeTeam, Team awayTeam) {
        List<Match> homeTeamLastFiveMatches = matchService.getLastFiveMatches(homeTeam);
        List<Match> awayTeamLstFiveMatches = matchService.getLastFiveMatches(awayTeam);

        double homeTeamForm = calculateTeamForm(homeTeamLastFiveMatches, homeTeam);
        double awayTeamForm = calculateTeamForm(awayTeamLstFiveMatches, awayTeam);

        return homeTeamForm / awayTeamForm;
    }

    private double calculateTeamForm(List<Match> lastFiveMatches, Team team) {
        if (lastFiveMatches.isEmpty()) {
            return 1;
        }

        int formPoints = 0;
        for (Match match : lastFiveMatches) {
            if (team.equals(match.getHomeTeam())) {
                if (match.getHomeTeamGoals() > match.getAwayTeamGoals()) {
                    formPoints += 3;
                } else if (match.getHomeTeamGoals() == match.getAwayTeamGoals()) {
                    formPoints += 1;
                }
            } else if (team.equals(match.getAwayTeam())) {
                if (match.getAwayTeamGoals() > match.getHomeTeamGoals()) {
                    formPoints += 3;
                } else if (match.getAwayTeamGoals() == match.getHomeTeamGoals()) {
                    formPoints += 1;
                }
            }
        }

        return (double) formPoints / (3 * lastFiveMatches.size());
    }

    private int generateGoalsUsingPoissonDistribution(double scoreWeight) {
        double lambda = Math.max(0, scoreWeight);
        double p = Math.exp(-lambda);
        int goals = 0;
        double cumulativeProbability = p;

        double randomNumber = RANDOM.nextDouble();
        while (randomNumber > cumulativeProbability) {
            goals++;
            p *= lambda / goals;
            cumulativeProbability += p;
        }

        return goals;
    }
}
