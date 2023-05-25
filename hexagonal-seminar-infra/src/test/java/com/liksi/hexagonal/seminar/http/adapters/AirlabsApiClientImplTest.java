package com.liksi.hexagonal.seminar.http.adapters;

import com.liksi.hexagonal.seminar.http.exception.AirportNotFoundException;
import com.liksi.hexagonal.seminar.http.mapper.AirportMapperImpl;
import com.liksi.hexagonal.seminar.http.model.AirportDTO;
import com.liksi.hexagonal.seminar.http.model.AirportResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@MockitoSettings
class AirlabsApiClientImplTest {

    @Spy
    private AirportMapperImpl airportMapper;

    @Mock
    private AirportHttpClient airportHttpClient;

    private AirlabsApiClientImpl airlabsApiClient;

    @BeforeEach
    void setup() {
        airlabsApiClient = new AirlabsApiClientImpl(airportHttpClient, airportMapper);
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
}