package com.liksi.hexagonal.seminar.ports.persistence;

import com.liksi.hexagonal.seminar.model.Seminar;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FakeSeminarRepository implements SeminarRepository {

		private List<Seminar> seminarList = new ArrayList<>();

	   @Override
	   public Optional<Seminar> findById(UUID id) {
			  return seminarList.stream()
					  .filter(seminar -> seminar.id() == id)
					  .findFirst();
	   }

	   @Override
	   public Seminar create(Seminar seminar) {
			  seminarList.add(seminar);
			  return seminar;
	   }
}