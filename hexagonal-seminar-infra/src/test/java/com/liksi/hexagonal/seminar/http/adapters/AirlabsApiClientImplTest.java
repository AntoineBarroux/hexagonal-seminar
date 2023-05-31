package com.liksi.hexagonal.seminar.http.adapters;

import com.liksi.hexagonal.seminar.AbstractIntegrationTest;
import com.liksi.hexagonal.seminar.http.exception.AirportNotFoundException;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AirlabsApiClientImplTest extends AbstractIntegrationTest {

    private static MockWebServer mockWebServer;

    @Autowired
    private AirlabsApiClientImpl airlabsApiClient;

    @BeforeAll
    static void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start(61000);
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void should_correctly_get_airport_by_iata_code() throws InterruptedException {
        mockWebServer.enqueue(createMockResponseFromFixture("airport.json"));
        final var result = airlabsApiClient.getAirportByIataCode("RNS");
        assertThat(result.iataCode()).isEqualTo("RNS");
        assertThat(result.countryCode()).isEqualTo("FR");

        final var request = mockWebServer.takeRequest();
        assertThat(request.getRequestUrl().queryParameter("api_key")).isEqualTo("XXXXXX");
        assertThat(request.getRequestUrl().queryParameter("iata_code")).isEqualTo("RNS");
    }

    @Test
    void should_correctly_get_airports_from_list_of_iata_codes() throws InterruptedException {
        mockWebServer.enqueue(createMockResponseFromFixture("multiple-airports.json"));
        final var result = airlabsApiClient.getAirportsByIataCodes(List.of("RNS,CDG"));
        assertThat(result).isNotNull();
        assertThat(result.get(0).iataCode()).isEqualTo("CDG");
        assertThat(result.get(0).countryCode()).isEqualTo("FR");
        assertThat(result.get(1).iataCode()).isEqualTo("RNS");
        assertThat(result.get(1).countryCode()).isEqualTo("FR");

        final var request = mockWebServer.takeRequest();
        assertThat(request.getRequestUrl().queryParameter("api_key")).isEqualTo("XXXXXX");
        assertThat(request.getRequestUrl().queryParameter("iata_code")).isEqualTo("RNS,CDG");
    }

    @Test
    void should_throw_airport_not_found_if_response_is_empty() throws InterruptedException {
        mockWebServer.enqueue(createMockResponseFromFixture("airport-not-found.json"));
        assertThatThrownBy(() -> airlabsApiClient.getAirportByIataCode("EMPTY"))
                .isInstanceOf(AirportNotFoundException.class)
                .hasMessage("Airport EMPTY not found");
        final var request = mockWebServer.takeRequest();
        assertThat(request.getRequestUrl().queryParameter("api_key")).isEqualTo("XXXXXX");
        assertThat(request.getRequestUrl().queryParameter("iata_code")).isEqualTo("EMPTY");
    }

    @Test
    void should_correctly_get_routes_from_departure_by_iata_code() throws InterruptedException {
        mockWebServer.enqueue(createMockResponseFromFixture("route.json"));
        final var result = airlabsApiClient.getRoutesFromDepartureByIataCode("RNS");
        assertThat(result).isNotNull();
        assertThat(result).hasSize(22);
        assertThat(result.get(0).arrIata()).isEqualTo("AMS");
        assertThat(result.get(0).depIata()).isEqualTo("RNS");

        final var request = mockWebServer.takeRequest();
        assertThat(request.getRequestUrl().queryParameter("api_key")).isEqualTo("XXXXXX");
        assertThat(request.getRequestUrl().queryParameter("dep_iata")).isEqualTo("RNS");
    }

    @Test
    void should_correctly_get_routes_from_departure_by_iata_code_empty_list() throws InterruptedException {
        mockWebServer.enqueue(createMockResponseFromFixture("route-empty.json"));
        final var result = airlabsApiClient.getRoutesFromDepartureByIataCode("EMPTY");
        assertThat(result).isNotNull();
        assertThat(result).hasSize(0);
        final var request = mockWebServer.takeRequest();
        assertThat(request.getRequestUrl().queryParameter("api_key")).isEqualTo("XXXXXX");
        assertThat(request.getRequestUrl().queryParameter("dep_iata")).isEqualTo("EMPTY");
    }
}