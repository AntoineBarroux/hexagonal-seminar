package com.liksi.hexagonal.seminar.http.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record RouteDTO (
        @JsonProperty("flight_number")
        String flightNumber,
        @JsonProperty("dep_iata")
        String depIata,
        @JsonProperty("dep_time")
        String depTime,
        @JsonProperty("dep_time_utc")
        String depTimeUtc,
        @JsonProperty("arr_iata")
        String arrIata,
        @JsonProperty("arr_terminals")
        List<String> arrTerminals,
        @JsonProperty("arr_time")
        String arrTime,
        @JsonProperty("arr_time_utc")
        String arrTimeUtc,
        @JsonProperty("duration")
        Integer duration
) { }
