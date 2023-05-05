package com.gkritas.footballsimrestapi.mapper;

import com.gkritas.footballsimrestapi.DTO.SaveGameDTO;
import com.gkritas.footballsimrestapi.model.SaveGame;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class SaveGameMapper {

    private final LeagueMapper leagueMapper;

    public SaveGameMapper(LeagueMapper leagueMapper) {
        this.leagueMapper = leagueMapper;
    }

    public SaveGameDTO toDTO(SaveGame saveGame) {
        SaveGameDTO dto = new SaveGameDTO();
        dto.setId(saveGame.getId());
        dto.setName(saveGame.getName());
        dto.setCreatedAt(saveGame.getCreatedAt());
        dto.setUpdatedAt(saveGame.getUpdatedAt());
        if (saveGame.getLeagues() != null) {
            dto.setLeagues(saveGame.getLeagues()
                    .stream()
                    .map(leagueMapper::toDTO).collect(Collectors.toList()));
        } else {
            dto.setLeagues(new ArrayList<>());
        }

        return dto;
    }

    public SaveGame toEntity(SaveGameDTO dto) {
        SaveGame saveGame = new SaveGame();
        saveGame.setId(dto.getId());
        saveGame.setName(dto.getName());
        saveGame.setCreatedAt(dto.getCreatedAt());
        saveGame.setUpdatedAt(dto.getUpdatedAt());
        if (dto.getLeagues() != null) {
            saveGame.setLeagues(dto.getLeagues()
                    .stream()
                    .map(leagueMapper::toEntity).collect(Collectors.toList()));
        } else {
            saveGame.setLeagues(new ArrayList<>());
        }

        return saveGame;
    }
}
