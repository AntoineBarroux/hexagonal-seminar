package com.liksi.hexagonal.seminar.http.adapters;

import com.liksi.hexagonal.seminar.http.exception.AirportNotFoundException;
import com.liksi.hexagonal.seminar.http.mapper.AirportMapperImpl;
import com.liksi.hexagonal.seminar.http.mapper.RouteMapperImpl;
import com.liksi.hexagonal.seminar.http.model.AirportDTO;
import com.liksi.hexagonal.seminar.http.model.AirportResponseDTO;
import com.liksi.hexagonal.seminar.http.model.RouteDTO;
import com.liksi.hexagonal.seminar.http.model.RouteResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@MockitoSettings
class AirlabsApiClientImplTest {

    @Spy
    private AirportMapperImpl airportMapper;

    @Spy
    private RouteMapperImpl routeMapper;

    @Mock
    private AirportHttpClient airportHttpClient;

    @Mock
    private RouteHttpClient routeHttpClient;

    private AirlabsApiClientImpl airlabsApiClient;

    @BeforeEach
    void setup() {
        airlabsApiClient = new AirlabsApiClientImpl(airportHttpClient, routeHttpClient, airportMapper, routeMapper);
    }

    @Test
    void should_correctly_get_airport_by_iata_code() {
        final var airport = new AirportDTO("Rennes", "RNS", "LFRN", 48.069025, -1.731943, "FR");
        final var response = new AirportResponseDTO(List.of(airport));
        when(airportHttpClient.getAirportByIataCode(anyString())).thenReturn(response);

        final var result = airlabsApiClient.getAirportByIataCode("RNS");
        assertThat(result).isNotNull();
        assertThat(result.iataCode()).isEqualTo("RNS");
    }

    @Test
    void should_throw_airport_not_found_if_response_is_empty() {
        final var response = new AirportResponseDTO(List.of());
        when(airportHttpClient.getAirportByIataCode(anyString())).thenReturn(response);

        assertThatThrownBy(() -> airlabsApiClient.getAirportByIataCode("RNS"))
                .isInstanceOf(AirportNotFoundException.class)
                .hasMessage("Airport RNS not found");
    }

    @Test
    void should_correctly_get_routes_from_departure_by_iata_code() {
        final var route = new RouteDTO("1030", "RNS", "09:25", "08:25", "AMS", List.of("2"), "11:00", "10:00", 95);
        final var response = new RouteResponseDTO(List.of(route));
        when(routeHttpClient.getRoutesFromDepartureByIataCode(anyString())).thenReturn(response);

        final var result = airlabsApiClient.getRoutesFromDepartureByIataCode("RNS");
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).depIata()).isEqualTo("RNS");
        assertThat(result.get(0).arrIata()).isEqualTo("AMS");
    }

    @Test
    void should_correctly_get_routes_from_departure_by_iata_code_empty_list() {
        final var response = new RouteResponseDTO(Collections.emptyList());
        when(routeHttpClient.getRoutesFromDepartureByIataCode(anyString())).thenReturn(response);

        final var result = airlabsApiClient.getRoutesFromDepartureByIataCode("RNS");
        assertThat(result).isNotNull();
        assertThat(result).hasSize(0);
    }
}