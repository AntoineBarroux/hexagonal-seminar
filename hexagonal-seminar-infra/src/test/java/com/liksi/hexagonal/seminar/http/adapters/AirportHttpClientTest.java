package com.liksi.hexagonal.seminar.http.adapters;

import com.liksi.hexagonal.seminar.AbstractIntegrationTest;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class AirportHttpClientTest extends AbstractIntegrationTest {

    private static MockWebServer mockWebServer;

    @Autowired
    private AirportHttpClient airportHttpClient;

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
    void should_correctly_get_airport_from_airlabs_api() throws InterruptedException {
        mockWebServer.enqueue(createMockResponseFromFixture("airport.json"));
        final var response = airportHttpClient.getAirportByIataCode("RNS");
        assertThat(response).isNotNull();
        assertThat(response.response().get(0).iataCode()).isEqualTo("RNS");
        assertThat(response.response().get(0).name()).isEqualTo("Rennes - Saint-Jacques");

        final var request = mockWebServer.takeRequest();
        assertThat(request.getRequestUrl().queryParameter("api_key")).isEqualTo("XXXXXX");
        assertThat(request.getRequestUrl().queryParameter("iata_code")).isEqualTo("RNS");
    }

    @Test
    void should_correctly_deserialize_empty_response() throws InterruptedException {
        mockWebServer.enqueue(createMockResponseFromFixture("airport-not-found.json"));
        final var response = airportHttpClient.getAirportByIataCode("EMPTY");
        assertThat(response).isNotNull();
        assertThat(response.response()).isEmpty();

        final var request = mockWebServer.takeRequest();
        assertThat(request.getRequestUrl().queryParameter("api_key")).isEqualTo("XXXXXX");
        assertThat(request.getRequestUrl().queryParameter("iata_code")).isEqualTo("EMPTY");
    }
}