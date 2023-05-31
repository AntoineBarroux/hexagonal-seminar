package com.liksi.hexagonal.seminar.http.adapters;

import com.liksi.hexagonal.seminar.AbstractIntegrationTest;
import com.liksi.hexagonal.seminar.http.exception.AirportNotFoundException;
import com.liksi.hexagonal.seminar.http.model.ClimatiqEmissionsRequest;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ClimatiqHttpClientTest extends AbstractIntegrationTest {

    private static MockWebServer mockWebServer;

    @Autowired
    private ClimatiqHttpClient climatiqHttpClient;

    @BeforeAll
    static void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start(62000);
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void should_correctly_get_emissions_between_two_known_airports() throws InterruptedException {
        mockWebServer.enqueue(createMockResponseFromFixture("emissions-between-known-airports.json"));
        final var response = climatiqHttpClient.getEmissionsBetweenAirports(new ClimatiqEmissionsRequest("RNS", "AMS", 20));
        assertThat(response).isNotNull();
        assertThat(response.co2e()).isEqualTo(3405);
        assertThat(response.co2eUnit()).isEqualTo("kg");

        final var request = mockWebServer.takeRequest();
        assertThat(request.getHeader(HttpHeaders.AUTHORIZATION)).isEqualTo("Bearer XXXXXX");
        assertThat(request.getBody().readUtf8()).isEqualTo("{\"legs\":[{\"class\":\"economy\",\"from\":\"RNS\",\"to\":\"AMS\",\"passengers\":20}]}");
    }

    @Test
    void should_correctly_throw_business_exception_if_departure_airport_not_found() throws InterruptedException {
        mockWebServer.enqueue(createMockResponse400FromFixture("emissions-between-unknown-airports.json"));

        assertThatThrownBy(() -> climatiqHttpClient.getEmissionsBetweenAirports(new ClimatiqEmissionsRequest("FHDSDSLfds", "AMS", 20)))
                .isInstanceOf(AirportNotFoundException.class)
                .hasMessage("Departure or arrival airport not found");

        final var request = mockWebServer.takeRequest();
        assertThat(request.getHeader(HttpHeaders.AUTHORIZATION)).isEqualTo("Bearer XXXXXX");
        assertThat(request.getBody().readUtf8()).isEqualTo("{\"legs\":[{\"class\":\"economy\",\"from\":\"FHDSDSLfds\",\"to\":\"AMS\",\"passengers\":20}]}");

    }

    @Test
    void should_correctly_throw_business_exception_if_arrival_airport_not_found() throws InterruptedException {
        mockWebServer.enqueue(createMockResponse400FromFixture("emissions-between-unknown-airports.json"));

        assertThatThrownBy(() -> climatiqHttpClient.getEmissionsBetweenAirports(new ClimatiqEmissionsRequest("RNS", "FHDSDSLfds", 20)))
                .isInstanceOf(AirportNotFoundException.class)
                .hasMessage("Departure or arrival airport not found");

        final var request = mockWebServer.takeRequest();
        assertThat(request.getHeader(HttpHeaders.AUTHORIZATION)).isEqualTo("Bearer XXXXXX");
        assertThat(request.getBody().readUtf8()).isEqualTo("{\"legs\":[{\"class\":\"economy\",\"from\":\"RNS\",\"to\":\"FHDSDSLfds\",\"passengers\":20}]}");
    }

    @Test
    void should_correctly_throw_business_exception_if_both_departure_and_arrival_airport_not_found() throws InterruptedException {
        mockWebServer.enqueue(createMockResponse400FromFixture("emissions-between-unknown-airports.json"));

        assertThatThrownBy(() -> climatiqHttpClient.getEmissionsBetweenAirports(new ClimatiqEmissionsRequest("FHDSDSLfds", "FHDSDSLfds", 20)))
                .isInstanceOf(AirportNotFoundException.class)
                .hasMessage("Departure or arrival airport not found");

        final var request = mockWebServer.takeRequest();
        assertThat(request.getHeader(HttpHeaders.AUTHORIZATION)).isEqualTo("Bearer XXXXXX");
        assertThat(request.getBody().readUtf8()).isEqualTo("{\"legs\":[{\"class\":\"economy\",\"from\":\"FHDSDSLfds\",\"to\":\"FHDSDSLfds\",\"passengers\":20}]}");
    }
}