package com.gkritas.footballsimrestapi.service;

import com.gkritas.footballsimrestapi.exception.PlayerNotFoundException;
import com.gkritas.footballsimrestapi.model.Player;
import com.gkritas.footballsimrestapi.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Player getPlayerById(Long id) {
        return playerRepository.findById(id).orElseThrow(() -> new PlayerNotFoundException("Player not found with id: " + id));
    }

    public void deletePlayerById(Long id) {
        Player player = getPlayerById(id);
        playerRepository.delete(player);
    }

    public Player createdPlayer(Player player) {
        return playerRepository.save(player);
    }

    public Player updatedPlayer(Long id, Player player) {
        Player updatedPlayer = getPlayerById(id);
        updatedPlayer.setFirstName(player.getFirstName());
        updatedPlayer.setLastName(player.getLastName());
        updatedPlayer.setDateOfBirth(player.getDateOfBirth());
        updatedPlayer.setPosition(player.getPosition());
        updatedPlayer.setTeam(player.getTeam());
        return playerRepository.save(updatedPlayer);
    }
}
