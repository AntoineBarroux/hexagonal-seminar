package com.liksi.hexagonal.seminar.ports.http;

public class FakeClimatiqApiClient implements ClimatiqApiClient {

    // TODO
    @Override
    public Long getEmissionsBetweenAirports(final String departureIataCode, final String arrivalIataCode, final int passengersCount) {
        return null;
    }
}
