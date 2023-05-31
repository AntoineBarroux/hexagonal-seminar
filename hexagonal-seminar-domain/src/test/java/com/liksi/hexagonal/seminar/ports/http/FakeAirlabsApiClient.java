package com.liksi.hexagonal.seminar.ports.http;

import com.liksi.hexagonal.seminar.model.Airport;
import com.liksi.hexagonal.seminar.model.Route;

import java.util.ArrayList;
import java.util.List;

public class FakeAirlabsApiClient implements AirlabsApiClient {

    private List<Airport> airports = new ArrayList<>();
    private List<Route> routes = new ArrayList<>();

    public void addAirport(Airport airport) {
        airports.add(airport);
    }

    public void addRoute(Route route) {
        routes.add(route);
    }

    @Override
    public Airport getAirportByIataCode(final String iataCode) {
        return airports.stream()
                .filter(airport -> airport.iataCode().equals(iataCode))
                .findFirst()
                .orElseThrow();
    }

    @Override
    public List<Airport> getAirportsByIataCodes(final List<String> iataCodes) {
        return airports.stream()
                .filter(airport -> iataCodes.contains(airport.iataCode()))
                .toList();
    }

    @Override
    public List<Route> getRoutesFromDepartureByIataCode(final String departureIataCode) {
        return routes.stream()
                .filter(route -> route.depIata().equals(departureIataCode))
                .toList();
    }
}
