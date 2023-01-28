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
@RequiredArgsConstructor
public class PlayerController implements PlayerOperations {

    private final PlayerService playerService;
    private final AddressService addressService;
    private final PhoneNumberService phoneNumberService;

    public Player getPlayer(int playerId) {
        Optional<Player> player = playerService.getPlayer(playerId);
        if (player.isEmpty()) {
            throw new PlayerNotFoundException("Player id not found - " + playerId);
        }
        return player.get();
    }

    public ResponseEntity<PlayerPagingOutput> getPlayers(
            String email,
            String lastName,
            String ageBetween,
            String sortBy,
            String order,
            int page,
            int resultsPerPage
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
        return ResponseEntity.ok(new PlayerPagingOutput(players.getContent(), pageable.getPageNumber()+1,
                pageable.getPageSize(), players.getTotalElements()));
    }

    private Sort createSort(String sortBy, String order) {
        Sort.Direction direction;
        if (order.equals("asc"))
            direction = Sort.Direction.ASC;
        else
            direction = Sort.Direction.DESC;
        return Sort.by(direction, sortBy);
    }

    public Player addPlayer(Player player) {
        return playerService.createPlayer(player);
    }

    public String deletePlayer(int id) {
        getPlayer(id);
        playerService.deletePlayer(id);
        return "Deleted player with id " + id;
    }

    public Player updatePlayerDetails(Player player) {
        getPlayer(player.getId());
        return playerService.updatePlayer(player);
    }

    public Player addAddressToPlayer(int player_id, Address address) {
        Player player = getPlayer(player_id);
        player.setAddress(address);
        return playerService.updatePlayer(player);
    }

    public Player deleteAddressForPlayer(int player_id) {
        Player player = getPlayer(player_id);
        Address address = player.getAddress();
        player.setAddress(null);
        addressService.deleteAddress(address.getId());
        return player;
    }

    public Player addPhoneNumberToPlayer(int player_id, PhoneNumber phoneNumber) {
        Player player = getPlayer(player_id);
        player.addPhoneNumber(phoneNumber);
        return playerService.updatePlayer(player);
    }

    public Player deletePhoneNumberForPlayer(int player_id, int phonenumber_id) {
        phoneNumberService.deletePhoneNumber(phonenumber_id);
        return getPlayer(player_id);
    }
}
