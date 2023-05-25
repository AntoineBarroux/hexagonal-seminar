package com.liksi.hexagonal.seminar.http.adapters;

import com.liksi.hexagonal.seminar.http.exception.AirportNotFoundException;
import com.liksi.hexagonal.seminar.http.mapper.AirportMapper;
import com.liksi.hexagonal.seminar.model.Airport;
import com.liksi.hexagonal.seminar.ports.http.AirlabsApiClient;
import org.springframework.stereotype.Service;

@Service
public class AirlabsApiClientImpl implements AirlabsApiClient {

    private final AirportHttpClient airportHttpClient;
    private final AirportMapper airportMapper;

    public AirlabsApiClientImpl(final AirportHttpClient airportHttpClient, final AirportMapper airportMapper) {
        this.airportHttpClient = airportHttpClient;
        this.airportMapper = airportMapper;
    }

    @Override
    public Airport getAirportByIataCode(final String iataCode) {
        return airportHttpClient.getAirportByIataCode(iataCode).response().stream()
                .findFirst()
                .map(airportMapper::toModel)
                .orElseThrow(() -> new AirportNotFoundException("Airport " + iataCode + " not found"));
    }
}
