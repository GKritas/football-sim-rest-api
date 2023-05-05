package com.gkritas.footballsimrestapi.service;

import com.gkritas.footballsimrestapi.exception.SaveGameNotFoundException;
import com.gkritas.footballsimrestapi.model.SaveGame;
import com.gkritas.footballsimrestapi.repository.SaveGameRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaveGameService {

    private final SaveGameRepository saveGameRepository;

    public SaveGameService(SaveGameRepository saveGameRepository) {
        this.saveGameRepository = saveGameRepository;
    }

    public List<SaveGame> findAllSaveGames() {
        return saveGameRepository.findAll();
    }

    public SaveGame findSaveGameById(Long id) {
        return saveGameRepository
                .findById(id)
                .orElseThrow(() ->
                        new SaveGameNotFoundException("Save Game not found with id: " + id));
    }
    public void deleteSaveGameById(Long id) {
        SaveGame saveGame = findSaveGameById(id);
        saveGameRepository.delete(saveGame);
    }

    public SaveGame createSaveGame(SaveGame saveGame) {
        return saveGameRepository.save(saveGame);
    }

    public SaveGame updateSaveGame(Long id, SaveGame saveGame) {
        SaveGame updatedSaveGame = findSaveGameById(id);
        updatedSaveGame.setName(saveGame.getName());
        updatedSaveGame.setCreatedAt(saveGame.getCreatedAt());
        updatedSaveGame.setUpdatedAt(saveGame.getUpdatedAt());
        updatedSaveGame.setLeagues(saveGame.getLeagues());

        return saveGameRepository.save(updatedSaveGame);
    }
}
