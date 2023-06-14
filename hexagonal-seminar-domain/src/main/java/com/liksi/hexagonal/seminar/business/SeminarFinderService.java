package com.liksi.hexagonal.seminar.business;

import com.liksi.hexagonal.seminar.model.Airport;
import com.liksi.hexagonal.seminar.model.Route;
import com.liksi.hexagonal.seminar.model.RouteConsommation;
import com.liksi.hexagonal.seminar.model.Seminar;
import com.liksi.hexagonal.seminar.ports.http.AirlabsApiClient;
import com.liksi.hexagonal.seminar.ports.persistence.SeminarRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SeminarFinderService {

    private final AirlabsApiClient airlabsApiClient;
    private final SeminarRepository seminarRepository;
    private final BestMatchFinderFactory bestMatchFinderFactory;


    public SeminarFinderService(final AirlabsApiClient airlabsApiClient,
            final SeminarRepository seminarRepository,
            final BestMatchFinderFactory dichotomyHelperFactory) {
        this.airlabsApiClient = airlabsApiClient;
        this.seminarRepository = seminarRepository;
        this.bestMatchFinderFactory = dichotomyHelperFactory;
    }

    public Optional<Seminar> findSeminarDestinationFrom(String departureIataCode, int passengersCount, Long maxConsommation) {
        final var departureAirport = airlabsApiClient.getAirportByIataCode(departureIataCode);

        final var existingRoutesFromDeparture = airlabsApiClient.getRoutesFromDepartureByIataCode(departureIataCode);
        final var relatedAirports = getDistinctAirportsFromRoutes(existingRoutesFromDeparture);
        final var existingRoutesMatchingConstraints = filterDistinctRoutesMatchingConstraints(departureAirport, existingRoutesFromDeparture, relatedAirports);

        return getBestMatch(existingRoutesMatchingConstraints, passengersCount, maxConsommation)
                .map(routeConsommation -> new Seminar(
                        departureAirport,
                        getAirport(relatedAirports, routeConsommation.route().arrIata()),
                        routeConsommation,
                        passengersCount
                ));
    }

    private List<Airport> getDistinctAirportsFromRoutes(final List<Route> routes) {
        return airlabsApiClient.getAirportsByIataCodes(routes.stream()
                .map(Route::arrIata)
                .distinct()
                .collect(Collectors.toList())
        );
    }

    private List<Route> filterDistinctRoutesMatchingConstraints(final Airport departureAirport, final List<Route> existingRoutesFromDeparture, final List<Airport> relatedAirports) {
        final var existingSeminars = seminarRepository.findAllByStartDateAfter(LocalDate.now().minusYears(5));
        return existingRoutesFromDeparture.stream()
                .filter(route -> doesNotConcernDepartureCountry(getAirport(relatedAirports, route.arrIata()), departureAirport))
                .filter(route -> doesNotConcernCountryFromAnExistingSeminar(getAirport(relatedAirports, route.arrIata()), existingSeminars))
                .filter(distinctByKey(Route::arrIata))
                .sorted(Comparator.comparing(Route::duration))
                .toList();
    }

    private static boolean doesNotConcernDepartureCountry(final Airport arrivalAirport, final Airport departureAirport) {
        return !arrivalAirport.countryCode().equals(departureAirport.countryCode());
    }

    private static boolean doesNotConcernCountryFromAnExistingSeminar(final Airport airport, final List<Seminar> existingSeminars) {
        return existingSeminars.stream()
                .noneMatch(seminar -> airport.countryCode().equals(seminar.arrival().countryCode()));
    }

    private Optional<RouteConsommation> getBestMatch(List<Route> existingRoutesMatchingConstraints, int passengersCount, Long maxConsommation) {
        return bestMatchFinderFactory.defaultFinder().getBestMatch(existingRoutesMatchingConstraints, passengersCount, maxConsommation);
    }

    private static Airport getAirport(final List<Airport> airports, final String iataCode) {
        return airports.stream()
                .filter(airport -> airport.iataCode().equals(iataCode))
                .findFirst()
                .orElseThrow();
    }

    public static <T> Predicate<T> distinctByKey(
            Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
