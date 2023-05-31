package com.liksi.hexagonal.seminar.ports.persistence;

import com.liksi.hexagonal.seminar.model.Seminar;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SeminarRepository {
	   Optional<Seminar> findById(UUID id);

	   Seminar create(Seminar seminar);

	   List<Seminar> findAllByStartDateAfter(LocalDate startDate);

	   List<Seminar> listAll();

	   void deleteById(UUID id);
}
