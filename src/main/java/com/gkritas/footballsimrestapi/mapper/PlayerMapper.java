package com.gkritas.footballsimrestapi.mapper;

import com.gkritas.footballsimrestapi.DTO.PlayerDTO;
import com.gkritas.footballsimrestapi.model.Player;
import com.gkritas.footballsimrestapi.service.TeamService;
import org.springframework.stereotype.Component;

@Component
public class PlayerMapper {

    private final TeamService teamService;

    public PlayerMapper(TeamService teamService) {
        this.teamService = teamService;
    }

    public PlayerDTO toDTO(Player player) {
        PlayerDTO dto = new PlayerDTO();
        dto.setId(player.getId());
        dto.setFirstName(player.getFirstName());
        dto.setLastName(player.getLastName());
        dto.setDateOfBirth(player.getDateOfBirth());
        dto.setPosition(player.getPosition());
        dto.setTeamId(player.getTeam().getId());

        return dto;
    }

    public Player toEntity(PlayerDTO dto) {
        Player player = new Player();
        player.setId(dto.getId());
        player.setFirstName(dto.getFirstName());
        player.setLastName(dto.getLastName());
        player.setDateOfBirth(dto.getDateOfBirth());
        player.setPosition(dto.getPosition());
        player.setTeam(teamService.getTeamById(dto.getTeamId()));

        return player;
    }
}
