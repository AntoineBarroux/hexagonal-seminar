package com.liksi.hexagonal.seminar.model;

import java.util.List;

public record Route(
        String flightNumber,
        String depIata,
        String depTime,
        String depTimeUtc,
        String arrIata,
        List<String> arrTerminals,
        String arrTime,
        String arrTimeUtc,
        Integer duration
) { }
