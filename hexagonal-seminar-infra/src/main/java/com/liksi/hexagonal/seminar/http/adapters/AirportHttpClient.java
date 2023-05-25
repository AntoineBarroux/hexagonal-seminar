package com.liksi.hexagonal.seminar.http.adapters;

import com.liksi.hexagonal.seminar.http.model.AirportResponseDTO;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(url = "/airports", accept = "application/json")
public interface AirportHttpClient {
    @GetExchange
    AirportResponseDTO getAirportByIataCode(@RequestParam(value = "iata_code") final String iataCode);
}
