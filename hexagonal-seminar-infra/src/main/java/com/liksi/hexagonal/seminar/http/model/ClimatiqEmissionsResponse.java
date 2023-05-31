package com.liksi.hexagonal.seminar.http.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ClimatiqEmissionsResponse(
        Long co2e,
        @JsonProperty("co2e_unit")
        String co2eUnit
) {
}
