package com.liksi.hexagonal.seminar.ports.http;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class FakeClimatiqApiClient implements ClimatiqApiClient {

    private final List<ClimatiqEntry> consumption = new ArrayList<>();
    private final List<String> arrivalIataCodes = new ArrayList<>();

    @Override
    public Long getEmissionsBetweenAirports(final String departureIataCode, final String arrivalIataCode, final int passengersCount) {
        arrivalIataCodes.add(arrivalIataCode);
        return consumption.stream()
                .filter(entry -> entry.departureIata.equals(departureIataCode) && entry.arrivalIata.equals(arrivalIataCode))
                .findFirst()
                .map(climatiqEntry -> climatiqEntry.consumptionPerPassenger * passengersCount)
                .orElseThrow();
    }

    public void addClimatiqEntry(String departureIata, String arrivalIata, Long consumptionPerPassenger) {
        consumption.add(new ClimatiqEntry(departureIata, arrivalIata, consumptionPerPassenger));
    }

    public void checkUniqueCallPerIataCode() {
        if (new HashSet<>(arrivalIataCodes).size() != arrivalIataCodes.size()) {
            throw new IllegalStateException("Multiple calls to getEmissionsBetweenAirports with the same arrivalIataCode : " + String.join(",", arrivalIataCodes));
        }
    }

    public record ClimatiqEntry(
            String departureIata,
            String arrivalIata,
            Long consumptionPerPassenger
    ) { }

}
