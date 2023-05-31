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
class RouteHttpClientTest extends AbstractIntegrationTest {

    private static MockWebServer mockWebServer;

    @Autowired
    private RouteHttpClient routeHttpClient;

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
    void should_correctly_get_list_of_routes_from_departure_airport() throws InterruptedException {
        mockWebServer.enqueue(createMockResponseFromFixture("route.json"));
        final var response = routeHttpClient.getRoutesFromDepartureByIataCode("RNS");
        assertThat(response).isNotNull();
        assertThat(response.response()).hasSize(22);
        assertThat(response.response().get(0).arrIata()).isEqualTo("AMS");
        assertThat(response.response().get(0).depIata()).isEqualTo("RNS");

        final var request = mockWebServer.takeRequest();
        assertThat(request.getRequestUrl().queryParameter("api_key")).isEqualTo("XXXXXX");
        assertThat(request.getRequestUrl().queryParameter("dep_iata")).isEqualTo("RNS");
    }

    @Test
    void should_correctly_get_empty_list_if_no_routes_found() throws InterruptedException {
        mockWebServer.enqueue(createMockResponseFromFixture("route-empty.json"));
        final var response = routeHttpClient.getRoutesFromDepartureByIataCode("EMPTY");
        assertThat(response).isNotNull();
        assertThat(response.response()).hasSize(0);

        final var request = mockWebServer.takeRequest();
        assertThat(request.getRequestUrl().queryParameter("api_key")).isEqualTo("XXXXXX");
        assertThat(request.getRequestUrl().queryParameter("dep_iata")).isEqualTo("EMPTY");
    }
}