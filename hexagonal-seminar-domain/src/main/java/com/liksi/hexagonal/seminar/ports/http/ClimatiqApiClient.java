package com.liksi.hexagonal.seminar.ports.http;

public interface ClimatiqApiClient {

    Long getEmissionsBetweenAirports(String departureIataCode, String arrivalIataCode, int passengersCount);
}
