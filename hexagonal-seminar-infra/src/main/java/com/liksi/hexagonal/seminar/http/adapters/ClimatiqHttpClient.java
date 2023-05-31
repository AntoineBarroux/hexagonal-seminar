package com.liksi.hexagonal.seminar.http.adapters;

import com.liksi.hexagonal.seminar.http.model.ClimatiqEmissionsRequest;
import com.liksi.hexagonal.seminar.http.model.ClimatiqEmissionsResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange(value = "/travel/flights", accept = "application/json")
public interface ClimatiqHttpClient {

    @PostExchange
    ClimatiqEmissionsResponse getEmissionsBetweenAirports(@RequestBody final ClimatiqEmissionsRequest climatiqEmissionsRequest);
}
