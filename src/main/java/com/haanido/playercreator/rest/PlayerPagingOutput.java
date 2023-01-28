package com.haanido.playercreator.rest;

import com.haanido.playercreator.entity.Player;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PlayerPagingOutput {
    private final List<Player> content;
    private final int page;
    private final long results_per_page;
    private final long total_results;
}
