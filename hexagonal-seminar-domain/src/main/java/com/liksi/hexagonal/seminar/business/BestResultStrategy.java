package com.liksi.hexagonal.seminar.business;

import java.util.List;
import java.util.Optional;

public interface BestResultStrategy {
    Optional<RouteConsommation> getBestResult(List<RouteConsommation> consommations);
}
