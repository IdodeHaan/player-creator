package com.sogeti.playerrestapi.service;

import com.sogeti.playerrestapi.entity.Player;
import com.sogeti.playerrestapi.repo.PlayerRepository;
import com.sogeti.playerrestapi.rest.PlayerFilter;
import com.sogeti.playerrestapi.rest.PlayerNotFoundException;
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

    public Player getPlayer(int id) {
        Optional<Player>  player = playerRepository.findById(id);
        if (player.isEmpty()) {
            throw new PlayerNotFoundException("Player id not found - " + id);
        }
        return player.get();
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
        getPlayer(player.getId());
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
        getPlayer(player.getId());
        return playerRepository.save(player);
    }
}
