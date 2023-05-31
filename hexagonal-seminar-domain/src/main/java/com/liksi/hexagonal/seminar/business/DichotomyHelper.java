package com.liksi.hexagonal.seminar.business;

import com.liksi.hexagonal.seminar.business.exception.InvalidRequestException;
import com.liksi.hexagonal.seminar.model.Route;
import com.liksi.hexagonal.seminar.ports.http.ClimatiqApiClient;

import java.util.*;

public class DichotomyHelper {

    private final ClimatiqApiClient climatiqApiClient;
    private final BestResultStrategy bestResultStrategy;
    private final List<RouteConsommation> consommation;

    public DichotomyHelper(final ClimatiqApiClient climatiqApiClient, BestResultStrategy bestResultStrategy) {
        this.climatiqApiClient = climatiqApiClient;
        this.bestResultStrategy = bestResultStrategy;
        this.consommation = new ArrayList<>();
    }

    public RouteConsommation getBestMatch(List<Route> routes, int passengersCount, Long maxConsommation) {
        process(routes, passengersCount, maxConsommation);
        return bestResultStrategy.getBestResult(consommation);
    }

    private void process(List<Route> routes, int passengersCount, Long maxConsommation) {
        if (routes.isEmpty()) {
            throw new InvalidRequestException("No routes found for this departure airport");
        }
        final var index = (routes.size() / 2) - 1;
        final var route = routes.get(index);
        final var routeConsommation = climatiqApiClient.getEmissionsBetweenAirports(route.depIata(), route.arrIata(), passengersCount);
        if (routeConsommation < maxConsommation) {
            consommation.add(new RouteConsommation(route, routeConsommation));
            process(routes.subList(index, routes.size()), passengersCount, maxConsommation);
        } else {
            process(routes.subList(0, index), passengersCount, maxConsommation);
        }
    }
}
