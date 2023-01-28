package com.haanido.playercreator.rest;

import com.haanido.playercreator.entity.Address;
import com.haanido.playercreator.entity.PhoneNumber;
import com.haanido.playercreator.entity.Player;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
public interface PlayerOperations {

    @GetMapping("/players/{playerId}")
    public Player getPlayer(@PathVariable int playerId);

    @GetMapping("/players")
    public ResponseEntity<PagingOutputObject> getPlayers(@RequestBody PlayerFilter playerFilter);

    @PostMapping("/players")
    public Player addPlayer(@RequestBody Player player);

    @DeleteMapping("/players/{id}")
    public String deletePlayer(@PathVariable int id);

    @PutMapping("/players")
    public Player updatePlayerDetails(@RequestBody Player player);

    @PutMapping("/players/{player_id}/address")
    public Player addAddressToPlayer(@PathVariable int player_id, @RequestBody Address address);

    @DeleteMapping("/players/{player_id}/address")
    public Player deleteAddressForPlayer(@PathVariable int player_id);

    @PutMapping("/players/{player_id}/phonenumbers")
    public Player addPhoneNumberToPlayer(@PathVariable int player_id, @RequestBody PhoneNumber phoneNumber);

    @DeleteMapping("/players/{player_id}/phonenumbers/{phonenumber_id}")
    public Player deletePhoneNumberForPlayer(@PathVariable int player_id, @PathVariable int phonenumber_id);
}
