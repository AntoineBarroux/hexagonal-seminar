package com.liksi.hexagonal.seminar.business;

import com.liksi.hexagonal.seminar.model.DummyObject;
import com.liksi.hexagonal.seminar.ports.DummyObjectDAO;

import java.util.UUID;

public class DummyObjectCrudOperationsService {

    private final DummyObjectDAO dummyObjectDAO;

    public DummyObjectCrudOperationsService(final DummyObjectDAO dummyObjectDAO) {
        this.dummyObjectDAO = dummyObjectDAO;
    }

    public DummyObject get(UUID id) {
        return dummyObjectDAO.findById(id);
    }
}
