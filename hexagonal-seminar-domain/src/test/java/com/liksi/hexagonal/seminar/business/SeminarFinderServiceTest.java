package com.liksi.hexagonal.seminar.business;

import com.liksi.hexagonal.seminar.model.Airport;
import com.liksi.hexagonal.seminar.model.Seminar;
import com.liksi.hexagonal.seminar.ports.http.FakeAirlabsApiClient;
import com.liksi.hexagonal.seminar.ports.http.FakeClimatiqApiClient;
import com.liksi.hexagonal.seminar.ports.persistence.FakeSeminarRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class SeminarFinderServiceTest {

    private final FakeAirlabsApiClient airlabsApiClient = new FakeAirlabsApiClient();
    private final FakeClimatiqApiClient climatiqApiClient = new FakeClimatiqApiClient();
    private final FakeSeminarRepository seminarRepository = new FakeSeminarRepository();

    private final SeminarFinderService seminarFinderService = new SeminarFinderService(airlabsApiClient, climatiqApiClient, seminarRepository);

    @Test
    void should_correctly_return_max_consommation_route_below_max_consommation_other_countries_without_existing_seminar() {
        airlabsApiClient.addAirport("RNS", "FR");
        airlabsApiClient.addAirport("AMS", "NL");
        airlabsApiClient.addAirport("BUD", "HU");
        airlabsApiClient.addAirport("JFK", "US");

        airlabsApiClient.addRoute("RNS", "AMS", 90);
        airlabsApiClient.addRoute("RNS", "JFK", 360);
        airlabsApiClient.addRoute("RNS", "BUD", 120);

        climatiqApiClient.addClimatiqEntry("RNS", "AMS", 20L);
        climatiqApiClient.addClimatiqEntry("RNS", "JFK", 100L);
        climatiqApiClient.addClimatiqEntry("RNS", "BUD", 45L);

        final var bestMatch = seminarFinderService.findSeminarDestinationFrom("RNS", 20, 1000L).orElseThrow();

        assertThat(bestMatch.arrival().iataCode()).isEqualTo("BUD");
        assertThat(bestMatch.carbon()).isEqualTo(900);
    }

    @Test
    void should_correctly_return_max_consommation_route_below_max_consommation_same_country_without_existing_seminar() {
        airlabsApiClient.addAirport("RNS", "FR");
        airlabsApiClient.addAirport("AMS", "NL");
        airlabsApiClient.addAirport("BUD", "FR");
        airlabsApiClient.addAirport("JFK", "US");

        airlabsApiClient.addRoute("RNS", "AMS", 90);
        airlabsApiClient.addRoute("RNS", "JFK", 360);
        airlabsApiClient.addRoute("RNS", "BUD", 120);

        climatiqApiClient.addClimatiqEntry("RNS", "AMS", 20L);
        climatiqApiClient.addClimatiqEntry("RNS", "JFK", 100L);
        climatiqApiClient.addClimatiqEntry("RNS", "BUD", 45L);

        final var bestMatch = seminarFinderService.findSeminarDestinationFrom("RNS", 20, 1000L).orElseThrow();

        assertThat(bestMatch.arrival().iataCode()).isEqualTo("AMS");
        assertThat(bestMatch.carbon()).isEqualTo(400);
    }

    @Test
    void should_correctly_return_max_consommation_route_below_max_consommation_same_country_with_existing_seminar() {
        airlabsApiClient.addAirport("RNS", "FR");
        airlabsApiClient.addAirport("AMS", "NL");
        airlabsApiClient.addAirport("BUD", "HU");
        airlabsApiClient.addAirport("JFK", "US");

        airlabsApiClient.addRoute("RNS", "AMS", 90);
        airlabsApiClient.addRoute("RNS", "JFK", 360);
        airlabsApiClient.addRoute("RNS", "BUD", 120);

        climatiqApiClient.addClimatiqEntry("RNS", "AMS", 20L);
        climatiqApiClient.addClimatiqEntry("RNS", "JFK", 100L);
        climatiqApiClient.addClimatiqEntry("RNS", "BUD", 45L);

        seminarRepository.create(new Seminar(UUID.randomUUID(), new Airport("RNS", "FR"), new Airport("BUD", "HU"), LocalDate.now().minusYears(1), 20, 900));

        final var bestMatch = seminarFinderService.findSeminarDestinationFrom("RNS", 20, 1000L).orElseThrow();

        assertThat(bestMatch.arrival().iataCode()).isEqualTo("AMS");
        assertThat(bestMatch.carbon()).isEqualTo(400);
    }

    @Test
    void should_correctly_manage_no_suitable() {
        airlabsApiClient.addAirport("RNS", "FR");
        airlabsApiClient.addAirport("AMS", "NL");
        airlabsApiClient.addAirport("BUD", "HU");
        airlabsApiClient.addAirport("JFK", "US");

        airlabsApiClient.addRoute("RNS", "AMS", 90);
        airlabsApiClient.addRoute("RNS", "JFK", 360);
        airlabsApiClient.addRoute("RNS", "BUD", 120);

        climatiqApiClient.addClimatiqEntry("RNS", "AMS", 20L);
        climatiqApiClient.addClimatiqEntry("RNS", "JFK", 100L);
        climatiqApiClient.addClimatiqEntry("RNS", "BUD", 45L);

        final var bestMatch = seminarFinderService.findSeminarDestinationFrom("RNS", 20, 1L);

        assertThat(bestMatch).isEmpty();
    }

    @Test
    void should_call_airports_only_once_per_iata_code() {
        airlabsApiClient.addAirport("RNS", "FR");
        airlabsApiClient.addAirport("AMS", "NL");
        airlabsApiClient.addAirport("BUD", "HU");
        airlabsApiClient.addAirport("JFK", "US");

        airlabsApiClient.addRoute("RNS", "AMS", 90);
        airlabsApiClient.addRoute("RNS", "AMS", 90);
        airlabsApiClient.addRoute("RNS", "AMS", 90);
        airlabsApiClient.addRoute("RNS", "AMS", 90);
        airlabsApiClient.addRoute("RNS", "AMS", 90);
        airlabsApiClient.addRoute("RNS", "AMS", 90);
        airlabsApiClient.addRoute("RNS", "AMS", 90);
        airlabsApiClient.addRoute("RNS", "AMS", 90);
        airlabsApiClient.addRoute("RNS", "JFK", 360);
        airlabsApiClient.addRoute("RNS", "JFK", 360);
        airlabsApiClient.addRoute("RNS", "JFK", 360);
        airlabsApiClient.addRoute("RNS", "JFK", 360);
        airlabsApiClient.addRoute("RNS", "JFK", 360);
        airlabsApiClient.addRoute("RNS", "BUD", 120);
        airlabsApiClient.addRoute("RNS", "BUD", 120);

        climatiqApiClient.addClimatiqEntry("RNS", "AMS", 20L);
        climatiqApiClient.addClimatiqEntry("RNS", "JFK", 100L);
        climatiqApiClient.addClimatiqEntry("RNS", "BUD", 45L);

        seminarFinderService.findSeminarDestinationFrom("RNS", 20, 1000L);
        assertThat(FakeAirlabsApiClient.Verify.getMultipleAirportsCall()).isEqualTo("AMS,JFK,BUD");
    }

    @Test
    void should_call_climatiq_only_once_per_iata_code() {
        airlabsApiClient.addAirport("RNS", "FR");
        airlabsApiClient.addAirport("AMS", "NL");
        airlabsApiClient.addAirport("BUD", "HU");
        airlabsApiClient.addAirport("JFK", "US");

        airlabsApiClient.addRoute("RNS", "AMS", 90);
        airlabsApiClient.addRoute("RNS", "AMS", 90);
        airlabsApiClient.addRoute("RNS", "AMS", 90);
        airlabsApiClient.addRoute("RNS", "AMS", 90);
        airlabsApiClient.addRoute("RNS", "AMS", 90);
        airlabsApiClient.addRoute("RNS", "AMS", 90);
        airlabsApiClient.addRoute("RNS", "AMS", 90);
        airlabsApiClient.addRoute("RNS", "AMS", 90);
        airlabsApiClient.addRoute("RNS", "JFK", 360);
        airlabsApiClient.addRoute("RNS", "JFK", 360);
        airlabsApiClient.addRoute("RNS", "JFK", 360);
        airlabsApiClient.addRoute("RNS", "JFK", 360);
        airlabsApiClient.addRoute("RNS", "JFK", 360);
        airlabsApiClient.addRoute("RNS", "BUD", 120);
        airlabsApiClient.addRoute("RNS", "BUD", 120);

        climatiqApiClient.addClimatiqEntry("RNS", "AMS", 20L);
        climatiqApiClient.addClimatiqEntry("RNS", "JFK", 100L);
        climatiqApiClient.addClimatiqEntry("RNS", "BUD", 45L);

        seminarFinderService.findSeminarDestinationFrom("RNS", 20, 1000L);
        assertThat(FakeClimatiqApiClient.Verify.hasOnlyOneCallPerIataCode()).isTrue();
    }
}