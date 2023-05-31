package com.liksi.hexagonal.seminar.business;

import com.liksi.hexagonal.seminar.model.Airport;
import com.liksi.hexagonal.seminar.model.Route;
import com.liksi.hexagonal.seminar.model.Seminar;
import com.liksi.hexagonal.seminar.ports.http.AirlabsApiClient;
import com.liksi.hexagonal.seminar.ports.http.ClimatiqApiClient;

import java.time.LocalDate;
import java.util.*;

public class SeminarFinderService {

    private final AirlabsApiClient airlabsApiClient;
    private final ClimatiqApiClient climatiqApiClient;


    // TODO : sortir le stockage temporaire dans une autre classe dédiée pour découplage ?
    private static final Map<Route, Long> consommation = new HashMap<>();

    public SeminarFinderService(final AirlabsApiClient airlabsApiClient, final ClimatiqApiClient climatiqApiClient) {
        this.airlabsApiClient = airlabsApiClient;
        this.climatiqApiClient = climatiqApiClient;
    }

    public Seminar findSeminarDestinationFrom(String departureIataCode, int passengersCount, Long maxConsommation) {
        final var departureAirport = airlabsApiClient.getAirportByIataCode(departureIataCode);

        // TODO : ne garder que les routes qui concernent un pays où l'on n'est pas allé
        // TODO : appel avec plusieurs aéroports : rajouter une route getAirportsByIataCodes(List<String> iataCodes)
        // TODO : pour chaque route, y associer un country code, filtrer pour dégager ceux égaux au country code de départ ou ceux ou on est déja allés
        final var associatedRoutes = airlabsApiClient.getRoutesFromDepartureByIataCode(departureIataCode).stream()
                .sorted(Comparator.comparing(Route::duration))
                .toList();

        final var dichotomy = new DichotomyHelper(climatiqApiClient);
        dichotomy.process(associatedRoutes, passengersCount, maxConsommation);
        final var bestResult = dichotomy.getBestResult();
        final var arrivalAirport = airlabsApiClient.getAirportByIataCode(bestResult.getKey().arrIata());
        return new Seminar(
                UUID.randomUUID(),
                new Airport(departureAirport.iataCode(), departureAirport.countryCode()),
                new Airport(arrivalAirport.iataCode(), arrivalAirport.countryCode()),
                LocalDate.now(),
                passengersCount,
                bestResult.getValue()
        );
    }
}
