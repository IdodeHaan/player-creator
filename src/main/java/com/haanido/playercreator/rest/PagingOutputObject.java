package com.haanido.playercreator.rest;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PagingOutputObject<T> {
    private final List<T> content;
    private final int page;
    private final long results_per_page;
    private final long total_results;
}
