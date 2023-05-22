package com.liksi.hexagonal.seminar.ports;

import com.liksi.hexagonal.seminar.model.DummyObject;

import java.util.UUID;

public interface DummyObjectDAO {
    DummyObject findById(UUID id);
}
