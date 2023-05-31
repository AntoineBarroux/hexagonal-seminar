package com.liksi.hexagonal.seminar.business;

import java.util.List;

public interface BestResultStrategy {
    RouteConsommation getBestResult(List<RouteConsommation> consommations);
}
