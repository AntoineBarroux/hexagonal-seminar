package com.liksi.hexagonal.seminar.ports.persistence;

import com.liksi.hexagonal.seminar.model.DummyObject;
import com.liksi.hexagonal.seminar.model.Seminar;

import java.util.Optional;
import java.util.UUID;

public interface SeminarRepository {
    Optional<Seminar> findById(UUID id);

    Seminar create(Seminar seminar);
}
