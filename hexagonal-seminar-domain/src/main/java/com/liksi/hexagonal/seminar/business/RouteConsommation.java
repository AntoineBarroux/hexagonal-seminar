package com.liksi.hexagonal.seminar.business;

import com.liksi.hexagonal.seminar.model.Route;

public record RouteConsommation(
        Route route,
        Long consommation
) {
}
