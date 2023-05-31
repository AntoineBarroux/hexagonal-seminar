package com.liksi.hexagonal.seminar.business;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class MaxConsommationStrategy implements BestResultStrategy {
    @Override
    public Optional<RouteConsommation> getBestResult(final List<RouteConsommation> consommations) {
        return consommations.stream()
                .max(Comparator.comparing(RouteConsommation::consommation));
    }
}
