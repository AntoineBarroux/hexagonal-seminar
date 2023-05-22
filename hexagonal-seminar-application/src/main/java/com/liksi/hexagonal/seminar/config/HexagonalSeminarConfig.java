package com.liksi.hexagonal.seminar.config;

import com.liksi.hexagonal.seminar.adapters.DummyObjectJpaRepository;
import com.liksi.hexagonal.seminar.adapters.DummyObjectRepository;
import com.liksi.hexagonal.seminar.business.DummyObjectCrudOperationsService;
import com.liksi.hexagonal.seminar.mapper.DummyObjectMapper;
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
}
