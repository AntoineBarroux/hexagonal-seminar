package com.liksi.hexagonal.seminar.http.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Leg(
        String from,
        String to,
        int passengers,
        @JsonProperty("class")
        String travelClass
) {
    public Leg(String from, String to, int passengers) {
        this(from, to, passengers, "economy");
    }
}
