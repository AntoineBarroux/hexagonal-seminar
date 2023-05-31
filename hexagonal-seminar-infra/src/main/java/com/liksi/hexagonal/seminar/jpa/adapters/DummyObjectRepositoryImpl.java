package com.liksi.hexagonal.seminar.jpa.adapters;

import com.liksi.hexagonal.seminar.jpa.mapper.DummyObjectMapper;
import com.liksi.hexagonal.seminar.model.DummyObject;
import com.liksi.hexagonal.seminar.ports.persistence.DummyObjectRepository;
import jakarta.persistence.EntityNotFoundException;

import java.util.UUID;

public class DummyObjectRepositoryImpl implements DummyObjectRepository {

    private final DummyObjectJpaRepository dummyObjectJpaRepository;
    private final DummyObjectMapper dummyObjectMapper;

    public DummyObjectRepositoryImpl(final DummyObjectJpaRepository dummyObjectJpaRepository,
            final DummyObjectMapper dummyObjectMapper) {
        this.dummyObjectJpaRepository = dummyObjectJpaRepository;
        this.dummyObjectMapper = dummyObjectMapper;
    }

    @Override
    public DummyObject findById(final UUID id) {
        return dummyObjectJpaRepository.findById(id)
                .map(dummyObjectMapper::toModel)
                .orElseThrow(() -> new EntityNotFoundException("DummyObject " + id + " not found"));
    }
}
