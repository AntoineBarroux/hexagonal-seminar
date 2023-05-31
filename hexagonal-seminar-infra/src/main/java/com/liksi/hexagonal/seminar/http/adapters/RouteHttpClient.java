package com.liksi.hexagonal.seminar.http.adapters;

import com.liksi.hexagonal.seminar.http.model.RouteResponseDTO;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(url = "/routes", accept = "application/json")
public interface RouteHttpClient {

    @GetExchange
    RouteResponseDTO getRoutesFromDepartureByIataCode(@RequestParam(value = "dep_iata") String departureIataCode);
}
