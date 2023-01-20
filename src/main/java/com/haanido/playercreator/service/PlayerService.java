package com.haanido.playercreator.service;

import com.haanido.playercreator.entity.Player;
import com.haanido.playercreator.repo.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;

    public Optional<Player> getPlayer(int id) {
        return playerRepository.findById(id);
    }
    public Page<Player> getAllPlayers(Pageable pageable) {
        return playerRepository.findAll(pageable);
    }
    public Page<Player> getPlayersByLastName(String name, Pageable pageable) {
        return playerRepository.findPlayerByLastNameContaining(name, pageable);
    }

    public Page<Player> getPlayersByEmail(String name, Pageable pageable) {
        return playerRepository.findPlayerByEmailContaining(name, pageable);
    }
    public Page<Player> getPlayersByLastNameAndEmail(String lastName, String email, Pageable pageable) {
        return playerRepository.findPlayerByLastNameContainsAndEmailContains(lastName, email, pageable);
    }

    public Page<Player> getPlayersByAgeRange(Integer age1, Integer age2,Pageable pageable) {
        return playerRepository.findPlayerByAgeBetween(age1, age2, pageable);
    }
    public Player createPlayer(Player player) {
        player.setId(0);
        if (player.getAddress() != null) {
            player.getAddress().setId(0);
        }
        playerRepository.save(player);
        return player;
    }
    public void deletePlayer(int id) {
        playerRepository.deleteById(id);
    }

    public Player updatePlayer(Player player) {
        return playerRepository.save(player);
    }
}
