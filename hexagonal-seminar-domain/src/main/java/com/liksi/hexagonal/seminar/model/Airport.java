package com.liksi.hexagonal.seminar.model;

public record Airport (
        String name,
        String iataCode,
        String icaoCode,
        Double lat,
        Double lng,
        String countryCode
) { }