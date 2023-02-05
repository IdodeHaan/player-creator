package com.sogeti.playerrestapi.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PlayerFilter {
    private String email;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("age_lt")
    private int ageLowerThan;
    @JsonProperty("age_gt")
    private int ageGreaterThan;
    @JsonProperty("sort_by")
    private String sortBy;
    private String order;
    private int page;
    @JsonProperty("results_per_page")
    private int resultsPerPage;
}
