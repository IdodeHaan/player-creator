package com.sogeti.playerrestapi.rest;

import com.sogeti.playerrestapi.entity.Address;
import com.sogeti.playerrestapi.entity.PhoneNumber;
import com.sogeti.playerrestapi.entity.Player;
import com.sogeti.playerrestapi.model.PlayerAddRequest;
import com.sogeti.playerrestapi.model.PlayerAddResponse;
import com.sogeti.playerrestapi.model.PlayerResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
public interface PlayerOperations {

    @GetMapping("/players/{playerId}")
    public ResponseEntity<PlayerResponse> getPlayer(@PathVariable int playerId);

    @GetMapping("/players")
    public ResponseEntity<PagingOutputObject> getPlayers(@RequestBody PlayerFilter playerFilter);

    @PostMapping("/players")
    public ResponseEntity<PlayerAddResponse> addPlayer(@RequestBody PlayerAddRequest playerAddRequest);

    @DeleteMapping("/players/{id}")
    public ResponseEntity<String> deletePlayer(@PathVariable int id);

    @PutMapping("/players")
    public ResponseEntity<PlayerResponse> updatePlayerDetails(@RequestBody Player player);

    @PutMapping("/players/{player_id}/address")
    public Player addAddressToPlayer(@PathVariable int player_id, @RequestBody Address address);

    @DeleteMapping("/players/{player_id}/address")
    public Player deleteAddressForPlayer(@PathVariable int player_id);

    @PutMapping("/players/{player_id}/phonenumbers")
    public Player addPhoneNumberToPlayer(@PathVariable int player_id, @RequestBody PhoneNumber phoneNumber);

    @DeleteMapping("/players/{player_id}/phonenumbers/{phonenumber_id}")
    public Player deletePhoneNumberForPlayer(@PathVariable int player_id, @PathVariable int phonenumber_id);
}
