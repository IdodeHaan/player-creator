package com.haanido.playercreator.rest;

import com.haanido.playercreator.entity.PagingJsonOutput;
import com.haanido.playercreator.entity.Player;
import com.haanido.playercreator.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PlayerRestController {

    private final PlayerService playerService;

    @GetMapping("/players/{playerId}")
    public Player getPlayerById(@PathVariable int playerId) {
        Optional<Player> player = playerService.getPlayer(playerId);
        if (player.isEmpty()) {
            throw new PlayerNotFoundException("Player id not found - " + playerId);
        }
        return player.get();
    }

    @GetMapping("/players")
    public PagingJsonOutput getPlayers(
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "last_name", required = false) String lastName,
            @RequestParam(value = "age", required = false) String ageBetween,
            @RequestParam(value = "sort_by") String sortBy,
            @RequestParam(value = "order") String order,
            @RequestParam(value = "page") int page,
            @RequestParam(value = "results_per_page") int resultsPerPage
        )
    {
        Sort sort = createSort(sortBy, order);
        Pageable pageable = PageRequest.of(page-1, resultsPerPage, sort);
        Page<Player> players;

        if (ageBetween != null) {
            String[] tempAge = ageBetween.split(":");
            int age1 = Integer.parseInt(tempAge[0]);
            int age2 = Integer.parseInt(tempAge[1]);
            players = playerService.getPlayersByAgeRange(age1, age2, pageable);
        } else {
            if (email != null && lastName != null) {
                players = playerService.getPlayersByLastNameAndEmail(lastName, email, pageable);
            } else if (email != null) {
                players = playerService.getPlayersByEmail(email, pageable);
            } else if (lastName != null) {
                players = playerService.getPlayersByLastName(lastName, pageable);
            } else {
                players = playerService.getAllPlayers(pageable);
            }
        }
        return new PagingJsonOutput(players.getContent(), pageable.getPageNumber()+1,
                pageable.getPageSize(), players.getTotalElements());
    }

    private Sort createSort(String sortBy, String order) {
        Sort.Direction direction;
        if (order.equals("asc"))
            direction = Sort.Direction.ASC;
        else
            direction = Sort.Direction.DESC;
        return Sort.by(direction, sortBy);
    }

    @PostMapping("/players")
    public Player createPlayer(@RequestBody Player player) {
        return playerService.createPlayer(player);
    }

    @DeleteMapping("/players/{id}")
    public String deletePlayerById(@PathVariable int id) {
        getPlayerById(id);
        playerService.deletePlayer(id);
        return "Deleted player with id " + id;
    }

    @PutMapping("/players")
    public Player updatePlayer(@RequestBody Player player) {
        getPlayerById(player.getId());
        return playerService.updatePlayer(player);
    }
}
