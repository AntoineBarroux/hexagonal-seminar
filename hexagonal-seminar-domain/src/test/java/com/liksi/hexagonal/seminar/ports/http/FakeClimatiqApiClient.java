package com.liksi.hexagonal.seminar.ports.http;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FakeClimatiqApiClient implements ClimatiqApiClient {

    private final List<ClimatiqEntry> consumption = new ArrayList<>();

    @Override
    public Long getEmissionsBetweenAirports(final String departureIataCode, final String arrivalIataCode, final int passengersCount) {
        Verify.arrivalIataCodes.add(arrivalIataCode);
        return consumption.stream()
                .filter(entry -> entry.departureIata.equals(departureIataCode) && entry.arrivalIata.equals(arrivalIataCode))
                .findFirst()
                .map(climatiqEntry -> climatiqEntry.consumptionPerPassenger * passengersCount)
                .orElseThrow();
    }

    public void addClimatiqEntry(String departureIata, String arrivalIata, Long consumptionPerPassenger) {
        consumption.add(new ClimatiqEntry(departureIata, arrivalIata, consumptionPerPassenger));
    }

    public record ClimatiqEntry(
            String departureIata,
            String arrivalIata,
            Long consumptionPerPassenger
    ) { }

    public static class Verify {
        static List<String> arrivalIataCodes = new ArrayList<>();

        public static boolean hasOnlyOneCallPerIataCode() {
            return new HashSet<>(arrivalIataCodes).size() == arrivalIataCodes.size();
        }
    }
}
