package com.liksi.hexagonal.seminar.ports.http;

import com.liksi.hexagonal.seminar.model.Airport;

public interface AirlabsApiClient {
    Airport getAirportByIataCode(String iataCode);
}
