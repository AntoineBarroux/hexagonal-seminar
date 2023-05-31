package com.liksi.hexagonal.seminar.config;

import com.liksi.hexagonal.seminar.business.DummyObjectCrudOperationsService;
import com.liksi.hexagonal.seminar.http.adapters.*;
import com.liksi.hexagonal.seminar.http.mapper.AirportMapper;
import com.liksi.hexagonal.seminar.http.mapper.RouteMapper;
import com.liksi.hexagonal.seminar.jpa.adapters.DummyObjectJpaRepository;
import com.liksi.hexagonal.seminar.jpa.adapters.DummyObjectRepositoryImpl;
import com.liksi.hexagonal.seminar.jpa.mapper.DummyObjectMapper;
import com.liksi.hexagonal.seminar.ports.http.AirlabsApiClient;
import com.liksi.hexagonal.seminar.ports.http.ClimatiqApiClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HexagonalSeminarConfig {

    @Bean
    public DummyObjectRepositoryImpl dummyObjectRepository(
            final DummyObjectJpaRepository dummyObjectJpaRepository,
            final DummyObjectMapper dummyObjectMapper
    ) {
        return new DummyObjectRepositoryImpl(dummyObjectJpaRepository, dummyObjectMapper);
    }

    @Bean
    public DummyObjectCrudOperationsService dummyObjectCrudOperationsService(
            final DummyObjectRepositoryImpl dummyObjectRepository
    ) {
        return new DummyObjectCrudOperationsService(dummyObjectRepository);
    }

    @Bean
    public AirlabsApiClient airlabsApiClient(AirportHttpClient airportHttpClient, RouteHttpClient routeHttpClient, AirportMapper airportMapper, RouteMapper routeMapper) {
        return new AirlabsApiClientImpl(airportHttpClient, routeHttpClient, airportMapper, routeMapper);
    }

    @Bean
    public ClimatiqApiClient climatiqApiClient(ClimatiqHttpClient climatiqHttpClient) {
        return new ClimatiqApiClientImpl(climatiqHttpClient);
    }
}
