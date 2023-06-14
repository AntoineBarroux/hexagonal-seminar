package com.liksi.hexagonal.seminar.business;

import com.liksi.hexagonal.seminar.ports.http.ClimatiqApiClient;

public class BestMatchFinderFactory {

    private final ClimatiqApiClient climatiqApiClient;

    public BestMatchFinderFactory(final ClimatiqApiClient climatiqApiClient) {
        this.climatiqApiClient = climatiqApiClient;
    }

    public BestMatchFinder defaultFinder() {
        return new BestMatchFinder(climatiqApiClient, new MaxConsommationStrategy());
    }

    public BestMatchFinder maxConsommationStrategy() {
        return defaultFinder();
    }
}
