package com.haanido.playercreator.rest;

import com.haanido.playercreator.entity.Address;
import com.haanido.playercreator.entity.PhoneNumber;
import com.haanido.playercreator.entity.Player;
import com.haanido.playercreator.service.AddressService;
import com.haanido.playercreator.service.PhoneNumberService;
import com.haanido.playercreator.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    public ResponseEntity<PagingOutputObject> getPlayers(PlayerFilter playerFilter)
    {
        Page<Player> players = playerService.getPlayers(playerFilter);
        return ResponseEntity.ok(new PagingOutputObject(players.getContent(), playerFilter.getPage(),
                playerFilter.getResultsPerPage(), players.getTotalElements()));
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
