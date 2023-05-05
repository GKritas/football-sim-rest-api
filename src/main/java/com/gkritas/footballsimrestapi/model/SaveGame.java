package com.gkritas.footballsimrestapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "save_game")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaveGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "saveGame", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<League> leagues;

    public SaveGame(String name) {
        this.name = name;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.leagues = new ArrayList<>();
    }
}
