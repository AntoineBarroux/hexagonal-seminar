package com.liksi.hexagonal.seminar.business;

import com.liksi.hexagonal.seminar.model.DummyObject;
import com.liksi.hexagonal.seminar.ports.persistence.DummyObjectRepository;

import java.util.UUID;

public class DummyObjectCrudOperationsService {

    private final DummyObjectRepository dummyObjectDAO;

    public DummyObjectCrudOperationsService(final DummyObjectRepository dummyObjectDAO) {
        this.dummyObjectDAO = dummyObjectDAO;
    }

    public DummyObject get(UUID id) {
        return dummyObjectDAO.findById(id);
    }
}
