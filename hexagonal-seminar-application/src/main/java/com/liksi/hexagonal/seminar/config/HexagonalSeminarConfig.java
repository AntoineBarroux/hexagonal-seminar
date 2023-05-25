package com.liksi.hexagonal.seminar.config;

import com.liksi.hexagonal.seminar.business.DummyObjectCrudOperationsService;
import com.liksi.hexagonal.seminar.http.adapters.AirlabsApiClientImpl;
import com.liksi.hexagonal.seminar.http.adapters.AirportHttpClient;
import com.liksi.hexagonal.seminar.http.mapper.AirportMapper;
import com.liksi.hexagonal.seminar.jpa.adapters.DummyObjectJpaRepository;
import com.liksi.hexagonal.seminar.jpa.adapters.DummyObjectRepository;
import com.liksi.hexagonal.seminar.jpa.mapper.DummyObjectMapper;
import com.liksi.hexagonal.seminar.ports.http.AirlabsApiClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HexagonalSeminarConfig {

    @Bean
    public DummyObjectRepository dummyObjectRepository(
            final DummyObjectJpaRepository dummyObjectJpaRepository,
            final DummyObjectMapper dummyObjectMapper
    ) {
        return new DummyObjectRepository(dummyObjectJpaRepository, dummyObjectMapper);
    }

    @Bean
    public DummyObjectCrudOperationsService dummyObjectCrudOperationsService(
            final DummyObjectRepository dummyObjectRepository
    ) {
        return new DummyObjectCrudOperationsService(dummyObjectRepository);
    }

    @Bean
    public AirlabsApiClient airlabsApiClient(AirportHttpClient airportHttpClient, AirportMapper airportMapper) {
        return new AirlabsApiClientImpl(airportHttpClient, airportMapper);
    }
}
