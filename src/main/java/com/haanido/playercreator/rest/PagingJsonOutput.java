package com.haanido.playercreator.rest;

import com.haanido.playercreator.entity.Player;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class PagingJsonOutput {
    private final List<Player> players;
    private final int page;
    private final long results_per_page;
    private final long total_results;
}
