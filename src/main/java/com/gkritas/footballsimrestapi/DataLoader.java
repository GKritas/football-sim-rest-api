package com.gkritas.footballsimrestapi;

import com.gkritas.footballsimrestapi.model.*;
import com.gkritas.footballsimrestapi.repository.SaveGameRepository;
import com.gkritas.footballsimrestapi.service.*;
import jakarta.annotation.PreDestroy;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final LeagueService leagueService;
    private final SeasonService seasonService;
    private final TeamService teamService;
    private final SaveGameService saveGameService;
    private final SaveGameRepository saveGameRepository;

    public DataLoader(LeagueService leagueService, SeasonService seasonService, TeamService teamService, SaveGameService saveGameService,
                      SaveGameRepository saveGameRepository) {
        this.leagueService = leagueService;
        this.seasonService = seasonService;
        this.teamService = teamService;
        this.saveGameService = saveGameService;
        this.saveGameRepository = saveGameRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        addData();
    }

    public void addData() {

        SaveGame saveGame = SaveGame.builder()
                .name("George Kritas")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .leagues(new ArrayList<>())
                .build();
        saveGameService.createSaveGame(saveGame);

        League league = League.builder()
                .name("Premier League")
                .saveGame(saveGame)
                .seasons(new ArrayList<>())
                .teams(new ArrayList<>())
                .build();
        leagueService.createLeague(league);

        Season season = Season.builder()
                .year(2023)
                .startDate(LocalDate.of(2022, 8, 20))
                .endDate(LocalDate.of(2023, 6, 3))
                .league(league)
                .matches(new ArrayList<>())
                .build();
        seasonService.createSeason(season);

        List<Team> teams = new ArrayList<>();
        teams.add(new Team("Manchester City", 83, 87, 85, league));
        teams.add(new Team("Liverpool", 84, 82, 84, league));
        teams.add(new Team("Chelsea", 82, 82, 83, league));
        teams.add(new Team("Tottenham Hotspur", 80, 81, 84, league));
        teams.add(new Team("Manchester United", 81, 84, 81, league));
        teams.add(new Team("Arsenal", 81, 84, 80, league));
        teams.add(new Team("Newcastle United", 79, 78, 80, league));
        teams.add(new Team("West Ham United", 77, 82, 78, league));
        teams.add(new Team("Aston Villa", 79, 78, 78, league));
        teams.add(new Team("Leicester City", 75, 80, 79, league));
        teams.add(new Team("Wolverhampton Wanderers", 75, 79, 78, league));
        teams.add(new Team("Everton", 76, 76, 78, league));
        teams.add(new Team("Nottingham Forest", 77, 76, 75, league));
        teams.add(new Team("Fulham", 75, 77, 79, league));
        teams.add(new Team("Leeds United", 75, 76, 77, league));
        teams.add(new Team("Crystal Palace", 76, 77, 75, league));
        teams.add(new Team("Brighton & Hove Albion", 77, 76, 75, league));
        teams.add(new Team("Brentford", 75, 75, 76, league));
        teams.add(new Team("Southampton", 76, 72, 76, league));
        teams.add(new Team("AFC Bournemouth", 73, 73, 75, league));

        for (Team team : teams) {
            teamService.createTeam(team);
        }
    }

    @PreDestroy
    public void deleteData() {
        saveGameRepository.deleteAll();
    }
}

