package com.gkritas.footballsimrestapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "team")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Integer defence;

    @Column(nullable = false)
    private Integer midfield;

    @Column(nullable = false)
    private Integer attack;

    @Column(nullable = false)
    private Integer points;

    @Column(nullable = false)
    private Integer goalsScored;

    @Column(nullable = false)
    private Integer goalsConceded;

    @Column(nullable = false)
    private Integer wins;

    @Column(nullable = false)
    private Integer draws;

    @Column(nullable = false)
    private Integer losses;

    @ManyToOne
    @JoinColumn(name = "league_id", nullable = false)
    private League league;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Player> players;

    public Team(String name, int defence, int midfield, int attack, League league) {
        this.name = name;
        this.defence = defence;
        this.midfield = midfield;
        this.attack = attack;
        this.points = 0;
        this.goalsScored = 0;
        this.goalsConceded = 0;
        this.wins = 0;
        this.draws = 0;
        this.losses = 0;
        this.league = league;
        this.players = new ArrayList<>();
    }
}
