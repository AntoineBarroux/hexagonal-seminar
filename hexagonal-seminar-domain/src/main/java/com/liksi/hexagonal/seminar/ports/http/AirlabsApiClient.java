package com.liksi.hexagonal.seminar.ports.http;

import com.liksi.hexagonal.seminar.model.Airport;
import com.liksi.hexagonal.seminar.model.Route;

import java.util.List;

public interface AirlabsApiClient {
    Airport getAirportByIataCode(String iataCode);
    List<Airport> getAirportsByIataCodes(List<String> iataCodes);
    List<Route> getRoutesFromDepartureByIataCode(String departureIataCode);
}
