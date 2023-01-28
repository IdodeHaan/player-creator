package com.haanido.playercreator.service;

import com.haanido.playercreator.entity.Player;
import com.haanido.playercreator.repo.PlayerRepository;
import com.haanido.playercreator.rest.PlayerFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;

    public Optional<Player> getPlayer(int id) {
        return playerRepository.findById(id);
    }

    public Page<Player> getPlayers(PlayerFilter playerFilter) {

        Sort sort = createSort(playerFilter.getSortBy(), playerFilter.getOrder());
        Pageable pageable = PageRequest.of(playerFilter.getPage()-1,
                playerFilter.getResultsPerPage(), sort);
        Page<Player> players;

        if (playerFilter.getAgeLowerThan() > 0 && playerFilter.getAgeGreaterThan() > 0) {
            players = playerRepository.findPlayerByAgeBetween(playerFilter.getAgeGreaterThan(),
                    playerFilter.getAgeLowerThan(), pageable);
        } else {
            String email = playerFilter.getEmail();
            String lastName = playerFilter.getLastName();
            if (email != null && lastName != null) {
                players = playerRepository.findPlayerByLastNameContainsAndEmailContains(lastName, email, pageable);
            } else if (email != null) {
                players = playerRepository.findPlayerByEmailContaining(email, pageable);
            } else if (lastName != null) {
                players = playerRepository.findPlayerByLastNameContaining(lastName, pageable);
            } else {
                players = playerRepository.findAll(pageable);
            }
        }
        return players;
    }

    private Sort createSort(String sortBy, String order) {
        Sort.Direction direction;
        if (order.equals("asc"))
            direction = Sort.Direction.ASC;
        else
            direction = Sort.Direction.DESC;
        return Sort.by(direction, sortBy);
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
