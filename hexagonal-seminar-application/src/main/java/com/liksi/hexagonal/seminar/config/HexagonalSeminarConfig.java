package com.liksi.hexagonal.seminar.config;

import com.liksi.hexagonal.seminar.business.BestMatchFinderFactory;
import com.liksi.hexagonal.seminar.business.SeminarFinderService;
import com.liksi.hexagonal.seminar.business.SeminarService;
import com.liksi.hexagonal.seminar.http.adapters.*;
import com.liksi.hexagonal.seminar.http.mapper.AirportMapper;
import com.liksi.hexagonal.seminar.http.mapper.RouteMapper;
import com.liksi.hexagonal.seminar.jpa.adapters.SeminarJpaRepository;
import com.liksi.hexagonal.seminar.jpa.adapters.SeminarRepositoryImpl;
import com.liksi.hexagonal.seminar.jpa.mapper.SeminarMapper;
import com.liksi.hexagonal.seminar.ports.http.AirlabsApiClient;
import com.liksi.hexagonal.seminar.ports.http.ClimatiqApiClient;
import com.liksi.hexagonal.seminar.ports.persistence.SeminarRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HexagonalSeminarConfig {

    @Bean
    public SeminarRepositoryImpl seminarRepository(
            final SeminarJpaRepository seminarJpaRepository,
            final SeminarMapper seminarMapper
    ) {
        return new SeminarRepositoryImpl(seminarJpaRepository, seminarMapper);
    }

    @Bean
    public SeminarService seminarService(
            final SeminarRepositoryImpl seminarRepository
    ) {
        return new SeminarService(seminarRepository);
    }

    @Bean
    public AirlabsApiClient airlabsApiClient(AirportHttpClient airportHttpClient, RouteHttpClient routeHttpClient, AirportMapper airportMapper, RouteMapper routeMapper) {
        return new AirlabsApiClientImpl(airportHttpClient, routeHttpClient, airportMapper, routeMapper);
    }

    @Bean
    public ClimatiqApiClient climatiqApiClient(ClimatiqHttpClient climatiqHttpClient) {
        return new ClimatiqApiClientImpl(climatiqHttpClient);
    }

    @Bean
    public BestMatchFinderFactory dichotomyHelperFactory(ClimatiqApiClient climatiqApiClient) {
        return new BestMatchFinderFactory(climatiqApiClient);
    }

    @Bean
    public SeminarFinderService seminarFinderService(AirlabsApiClient airlabsApiClient, SeminarRepository seminarRepository, BestMatchFinderFactory dichotomyHelperFactory) {
        return new SeminarFinderService(airlabsApiClient, seminarRepository, dichotomyHelperFactory);
    }
}
