package com.liksi.hexagonal.seminar.business;

import com.liksi.hexagonal.seminar.model.Airport;
import com.liksi.hexagonal.seminar.model.Route;
import com.liksi.hexagonal.seminar.model.Seminar;
import com.liksi.hexagonal.seminar.ports.http.AirlabsApiClient;
import com.liksi.hexagonal.seminar.ports.http.ClimatiqApiClient;
import com.liksi.hexagonal.seminar.ports.persistence.SeminarRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class SeminarFinderService {

    private final AirlabsApiClient airlabsApiClient;
    private final ClimatiqApiClient climatiqApiClient;
    private final SeminarRepository seminarRepository;


    public SeminarFinderService(final AirlabsApiClient airlabsApiClient,
            final ClimatiqApiClient climatiqApiClient,
            final SeminarRepository seminarRepository) {
        this.airlabsApiClient = airlabsApiClient;
        this.climatiqApiClient = climatiqApiClient;
        this.seminarRepository = seminarRepository;
    }

    public Seminar findSeminarDestinationFrom(String departureIataCode, int passengersCount, Long maxConsommation) {
        final var departureAirport = airlabsApiClient.getAirportByIataCode(departureIataCode);

        final var routes = airlabsApiClient.getRoutesFromDepartureByIataCode(departureIataCode);
        final var airports = airlabsApiClient.getAirportsByIataCodes(routes.stream().map(Route::arrIata).collect(Collectors.toList()));
        final var existingSeminars = seminarRepository.findAllByStartDateAfter(LocalDate.now().minusYears(5));

        final var filteredRoutes = routes.stream()
                .filter(route -> doesNotConcernDepartureCountry(getAirport(airports, route.arrIata()), departureAirport))
                .filter(route -> doesNotConcernCountryFromAnExistingSeminar(getAirport(airports, route.arrIata()), existingSeminars))
                .sorted(Comparator.comparing(Route::duration))
                .toList();

        final var dichotomy = new DichotomyHelper(climatiqApiClient, new MaxConsommationStrategy());
        final var bestResult = dichotomy.getBestMatch(filteredRoutes, passengersCount, maxConsommation);
        final var arrivalAirport = getAirport(airports, bestResult.route().arrIata());
        return new Seminar(
                UUID.randomUUID(),
                new Airport(departureAirport.iataCode(), departureAirport.countryCode()),
                new Airport(arrivalAirport.iataCode(), arrivalAirport.countryCode()),
                LocalDate.now(),
                passengersCount,
                bestResult.consommation()
        );
    }

    private static boolean doesNotConcernDepartureCountry(final Airport arrivalAirport, final Airport departureAirport) {
        return !arrivalAirport.countryCode().equals(departureAirport.countryCode());
    }

    private static boolean doesNotConcernCountryFromAnExistingSeminar(final Airport airport, final List<Seminar> existingSeminars) {
        return existingSeminars.stream()
                .noneMatch(seminar -> airport.countryCode().equals(seminar.arrival().countryCode()));
    }

    private static Airport getAirport(final List<Airport> airports, final String iataCode) {
        return airports.stream()
                .filter(airport -> airport.iataCode().equals(iataCode))
                .findFirst()
                .orElseThrow();
    }
}