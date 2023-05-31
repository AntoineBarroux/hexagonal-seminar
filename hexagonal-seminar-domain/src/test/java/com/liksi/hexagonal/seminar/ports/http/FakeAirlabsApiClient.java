package com.liksi.hexagonal.seminar.ports.http;

import com.liksi.hexagonal.seminar.model.Airport;
import com.liksi.hexagonal.seminar.model.Route;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class FakeAirlabsApiClient implements AirlabsApiClient {

    private final List<Airport> airports = new ArrayList<>();
    private final List<Route> routes = new ArrayList<>();

    private List<String> multipleAirportsCall = new ArrayList<>();

    public void addAirport(String iataCode, String countryCode) {
        airports.add(new Airport(iataCode, countryCode));
    }

    public void addRoute(String departureIata, String arrivalIata, int duration) {
        routes.add(new Route(null, departureIata, null, null, arrivalIata, null, null, null, duration));
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
        multipleAirportsCall.addAll(iataCodes);
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

    public void checkUniqueCallPerIataCode() {
        if (new HashSet<>(multipleAirportsCall).size() != multipleAirportsCall.size()) {
            throw new IllegalStateException("Multiple calls to getAirportsByIataCodes with the same iataCode : " + String.join(",", multipleAirportsCall));
        }
    }
}
