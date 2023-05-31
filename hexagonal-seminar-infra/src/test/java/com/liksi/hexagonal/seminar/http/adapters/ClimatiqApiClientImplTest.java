package com.liksi.hexagonal.seminar.http.adapters;

import com.liksi.hexagonal.seminar.http.model.ClimatiqEmissionsRequest;
import com.liksi.hexagonal.seminar.http.model.ClimatiqEmissionsResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@MockitoSettings
class ClimatiqApiClientImplTest {

    @Mock
    private ClimatiqHttpClient climatiqHttpClient;

    private ClimatiqApiClientImpl climatiqApiClient;

    @BeforeEach
    void setup() {
        climatiqApiClient = new ClimatiqApiClientImpl(climatiqHttpClient);
    }

    @Test
    void should_correctly_get_emissions_between_two_airports() {
        final var response = new ClimatiqEmissionsResponse(3405L, "kg");
        when(climatiqHttpClient.getEmissionsBetweenAirports(any(ClimatiqEmissionsRequest.class))).thenReturn(response);

        final var result = climatiqApiClient.getEmissionsBetweenAirports("RNS", "AMS", 20);
        assertThat(result).isEqualTo(3405);
    }
}