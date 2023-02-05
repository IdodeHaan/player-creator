package com.sogeti.playerrestapi.rest;

import com.sogeti.playerrestapi.entity.Address;
import com.sogeti.playerrestapi.entity.PhoneNumber;
import com.sogeti.playerrestapi.entity.Player;
import com.sogeti.playerrestapi.mapper.PlayerMapper;
import com.sogeti.playerrestapi.model.PlayerAddRequest;
import com.sogeti.playerrestapi.model.PlayerAddResponse;
import com.sogeti.playerrestapi.model.PlayerResponse;
import com.sogeti.playerrestapi.service.AddressService;
import com.sogeti.playerrestapi.service.PhoneNumberService;
import com.sogeti.playerrestapi.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PlayerController implements PlayerOperations {

    private final PlayerService playerService;
    private final AddressService addressService;
    private final PhoneNumberService phoneNumberService;
    private final PlayerMapper playerMapper;

    public ResponseEntity<PlayerResponse> getPlayer(int playerId) throws PlayerNotFoundException {
        PlayerResponse playerResponse = playerMapper.playerToPlayerResponse(playerService.getPlayer(playerId));
        return ResponseEntity.ok(playerResponse);
    }

    public ResponseEntity<PagingOutputObject> getPlayers(PlayerFilter playerFilter) {
        Page<Player> players = playerService.getPlayers(playerFilter);
        List<PlayerResponse> playerResponses = new ArrayList<>();
        for (Player p: players.getContent()) {
            playerResponses.add(playerMapper.playerToPlayerResponse(p));
        }
        return ResponseEntity.ok(new PagingOutputObject(playerResponses, playerFilter.getPage(),
                playerFilter.getResultsPerPage(), players.getTotalElements()));
    }
    public ResponseEntity<PlayerAddResponse> addPlayer(PlayerAddRequest playerAddRequest) {
        Player player = playerMapper.playerAddRequestToPlayer(playerAddRequest);
        Player createdPlayer = playerService.createPlayer(player);
        PlayerAddResponse playerAddResponse = playerMapper.playerToPlayerAddResponse(createdPlayer);
        return ResponseEntity.ok(playerAddResponse);
    }

    public ResponseEntity<String> deletePlayer(int id) {
        getPlayer(id);
        playerService.deletePlayer(id);
        return ResponseEntity.ok("Deleted player with id " + id);
    }

    public ResponseEntity<PlayerResponse> updatePlayerDetails(Player player) {
        Player updatedPlayer = playerService.updatePlayer(player);
        PlayerResponse playerResponse = playerMapper.playerToPlayerResponse(updatedPlayer);
        return ResponseEntity.ok(playerResponse);
    }

    public Player addAddressToPlayer(int player_id, Address address) throws PlayerNotFoundException {
        Player player = playerService.getPlayer(player_id);
        player.setAddress(address);
        return playerService.updatePlayer(player);
    }

    public Player deleteAddressForPlayer(int player_id) throws PlayerNotFoundException {
        Player player = playerService.getPlayer(player_id);
        Address address = player.getAddress();
        player.setAddress(null);
        addressService.deleteAddress(address.getId());
        return player;
    }

    public Player addPhoneNumberToPlayer(int player_id, PhoneNumber phoneNumber) throws PlayerNotFoundException {
        Player player = playerService.getPlayer(player_id);
        player.addPhoneNumber(phoneNumber);
        return playerService.updatePlayer(player);
    }

    public Player deletePhoneNumberForPlayer(int player_id, int phonenumber_id) {
        phoneNumberService.deletePhoneNumber(phonenumber_id);
        return playerService.getPlayer(player_id);
    }
}
