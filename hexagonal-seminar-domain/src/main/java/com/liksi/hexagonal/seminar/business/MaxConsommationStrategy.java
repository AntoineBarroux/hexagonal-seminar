package com.liksi.hexagonal.seminar.business;

import java.util.Comparator;
import java.util.List;

public class MaxConsommationStrategy implements BestResultStrategy {
    @Override
    public RouteConsommation getBestResult(final List<RouteConsommation> consommations) {
        return consommations.stream()
                .max(Comparator.comparing(RouteConsommation::consommation))
                .orElseThrow();
    }
}
