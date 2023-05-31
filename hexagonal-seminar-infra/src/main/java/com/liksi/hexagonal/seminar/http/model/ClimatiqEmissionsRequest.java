package com.liksi.hexagonal.seminar.http.model;

import java.util.List;

public record ClimatiqEmissionsRequest(
        List<Leg> legs
) {
    public ClimatiqEmissionsRequest(String from, String to, int passengers) {
        this(List.of(new Leg(from, to, passengers)));
    }
}
