package com.liksi.hexagonal.seminar.http.adapters;

import com.liksi.hexagonal.seminar.http.model.ClimatiqEmissionsRequest;
import com.liksi.hexagonal.seminar.ports.http.ClimatiqApiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ClimatiqApiClientImpl implements ClimatiqApiClient {

    private final Logger LOG = LoggerFactory.getLogger(ClimatiqApiClientImpl.class);

    private final ClimatiqHttpClient climatiqHttpClient;

    public ClimatiqApiClientImpl(final ClimatiqHttpClient climatiqHttpClient) {
        this.climatiqHttpClient = climatiqHttpClient;
    }

    @Override
    public Long getEmissionsBetweenAirports(final String departureIataCode, final String arrivalIataCode, final int passengersCount) {
        LOG.info("Getting emissions between airports {} and {} for {} passengers", departureIataCode, arrivalIataCode, passengersCount);
        return climatiqHttpClient.getEmissionsBetweenAirports(new ClimatiqEmissionsRequest(departureIataCode, arrivalIataCode, passengersCount))
                .co2e();
    }
}
