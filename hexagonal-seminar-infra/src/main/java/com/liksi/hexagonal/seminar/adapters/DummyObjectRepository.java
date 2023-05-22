package com.liksi.hexagonal.seminar.adapters;

import com.liksi.hexagonal.seminar.mapper.DummyObjectMapper;
import com.liksi.hexagonal.seminar.model.DummyObject;
import com.liksi.hexagonal.seminar.ports.DummyObjectDAO;
import jakarta.persistence.EntityNotFoundException;

import java.util.UUID;

public class DummyObjectRepository implements DummyObjectDAO {

    private final DummyObjectJpaRepository dummyObjectJpaRepository;
    private final DummyObjectMapper dummyObjectMapper;

    public DummyObjectRepository(final DummyObjectJpaRepository dummyObjectJpaRepository,
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
