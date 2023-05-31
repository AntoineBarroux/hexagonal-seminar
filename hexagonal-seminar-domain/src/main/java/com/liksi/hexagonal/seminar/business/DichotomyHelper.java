package com.liksi.hexagonal.seminar.business;

import com.liksi.hexagonal.seminar.business.exception.InvalidRequestException;
import com.liksi.hexagonal.seminar.model.Route;
import com.liksi.hexagonal.seminar.ports.http.ClimatiqApiClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DichotomyHelper {

    private final ClimatiqApiClient climatiqApiClient;
    private final Map<Route, Long> consommation;

    public DichotomyHelper(final ClimatiqApiClient climatiqApiClient) {
        this.climatiqApiClient = climatiqApiClient;
        this.consommation = new HashMap<>();
    }

    public void process(List<Route> routes, int passengersCount, Long maxConsommation) {
        if (routes.isEmpty()) {
            throw new InvalidRequestException("No routes found for this departure airport");
        }
        final var index = (routes.size() / 2) - 1;
        final var route = routes.get(index);
        final var routeConsommation = climatiqApiClient.getEmissionsBetweenAirports(route.depIata(), route.arrIata(), passengersCount);
        if (routes.size() > 1) {
            if (routeConsommation < maxConsommation) {
                consommation.put(route, routeConsommation);
                process(routes.subList(index, routes.size()), passengersCount, maxConsommation);
            } else {
                process(routes.subList(0, index), passengersCount, maxConsommation);
            }
        }
    }

    // TODO : si paramètre (on prend le plus loin, on prend le plus proche, on prend la + petite conso, ...)
    //  on peut passer ça dans une stratégie en paramètre du constructeur
    public Map.Entry<Route, Long> getBestResult() {
        return consommation.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .toList().get(0);
    }
}
