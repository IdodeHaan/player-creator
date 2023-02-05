package com.sogeti.playerrestapi.mapper;

import com.sogeti.playerrestapi.entity.Player;
import com.sogeti.playerrestapi.model.PlayerAddRequest;
import com.sogeti.playerrestapi.model.PlayerAddResponse;
import com.sogeti.playerrestapi.model.PlayerResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlayerMapper {

    private final ModelMapper modelMapper;

    public PlayerResponse playerToPlayerResponse(Player player) {
       return modelMapper.map(player, PlayerResponse.class);
    }

    public Player playerAddRequestToPlayer(PlayerAddRequest playerAddRequest) {
        return modelMapper.map(playerAddRequest, Player.class);
    }
    public PlayerAddResponse playerToPlayerAddResponse(Player player) {
        return modelMapper.map(player, PlayerAddResponse.class);
    }
}
