package com.liksi.hexagonal.seminar.ports.persistence;

import com.liksi.hexagonal.seminar.model.DummyObject;

import java.util.UUID;

public interface DummyObjectRepository {
    DummyObject findById(UUID id);
}
