package com.liksi.hexagonal.seminar.http.adapters;

import com.liksi.hexagonal.seminar.http.exception.AirportNotFoundException;
import com.liksi.hexagonal.seminar.http.mapper.AirportMapper;
import com.liksi.hexagonal.seminar.http.mapper.RouteMapper;
import com.liksi.hexagonal.seminar.model.Airport;
import com.liksi.hexagonal.seminar.model.Route;
import com.liksi.hexagonal.seminar.ports.http.AirlabsApiClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirlabsApiClientImpl implements AirlabsApiClient {

    private final AirportHttpClient airportHttpClient;
    private final RouteHttpClient routeHttpClient;
    private final AirportMapper airportMapper;
    private final RouteMapper routeMapper;

    public AirlabsApiClientImpl(
            final AirportHttpClient airportHttpClient,
            final RouteHttpClient routeHttpClient,
            final AirportMapper airportMapper,
            final RouteMapper routeMapper
    ) {
        this.airportHttpClient = airportHttpClient;
        this.routeHttpClient = routeHttpClient;
        this.airportMapper = airportMapper;
        this.routeMapper = routeMapper;
    }

    @Override
    public Airport getAirportByIataCode(final String iataCode) {
        return airportHttpClient.getAirportByIataCode(iataCode).response().stream()
                .findFirst()
                .map(airportMapper::toModel)
                .orElseThrow(() -> new AirportNotFoundException("Airport " + iataCode + " not found"));
    }

    @Override
    public List<Route> getRoutesFromDepartureByIataCode(final String departureIataCode) {
        return routeHttpClient.getRoutesFromDepartureByIataCode(departureIataCode).response().stream()
                .map(routeMapper::toModel)
                .toList();
    }
}
