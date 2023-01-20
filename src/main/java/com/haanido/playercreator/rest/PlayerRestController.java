package com.haanido.playercreator.rest;

import com.haanido.playercreator.entity.Address;
import com.haanido.playercreator.entity.PhoneNumber;
import com.haanido.playercreator.entity.Player;
import com.haanido.playercreator.service.AddressService;
import com.haanido.playercreator.service.PhoneNumberService;
import com.haanido.playercreator.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PlayerRestController {

    private final PlayerService playerService;
    private final AddressService addressService;
    private final PhoneNumberService phoneNumberService;

    @GetMapping("/players/{playerId}")
    public Player getPlayerById(@PathVariable int playerId) {
        Optional<Player> player = playerService.getPlayer(playerId);
        if (player.isEmpty()) {
            throw new PlayerNotFoundException("Player id not found - " + playerId);
        }
        return player.get();
    }

    @GetMapping("/players")
    public ResponseEntity<PagingJsonOutput> getPlayers(
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
        return ResponseEntity.ok(PagingJsonOutput.builder()
                .content(players.getContent())
                .page(pageable.getPageNumber())
                .results_per_page(pageable.getPageSize())
                .total_results(players.getTotalElements())
                .build());
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

    @PutMapping("/players/{player_id}/address")
    public Player addAddressToPlayer(@PathVariable int player_id, @RequestBody Address address) {
        Player player = getPlayerById(player_id);
        player.setAddress(address);
        return playerService.updatePlayer(player);
    }

    @DeleteMapping("/players/{player_id}/address")
    public Player deleteAddressForPlayer(@PathVariable int player_id) {
        Player player = getPlayerById(player_id);
        Address address = player.getAddress();
        player.setAddress(null);
        addressService.deleteAddress(address.getId());
        return player;
    }

    @PutMapping("/players/{player_id}/phonenumbers")
    public Player addPhoneNumberToPlayer(@PathVariable int player_id, @RequestBody PhoneNumber phoneNumber) {
        Player player = getPlayerById(player_id);
        player.addPhoneNumber(phoneNumber);
        return playerService.updatePlayer(player);
    }

    @DeleteMapping("/players/{player_id}/phonenumbers/{phonenumber_id}")
    public Player deletePhoneNumberForPlayer(@PathVariable int player_id, @PathVariable int phonenumber_id) {
        phoneNumberService.deletePhoneNumber(phonenumber_id);
        return getPlayerById(player_id);
    }
}
