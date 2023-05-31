package com.liksi.hexagonal.seminar.ports.persistence;

import com.liksi.hexagonal.seminar.model.Seminar;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FakeSeminarRepository implements SeminarRepository {

    private List<Seminar> seminarList = new ArrayList<>();

    @Override
    public Optional<Seminar> findById(UUID id) {
        return seminarList.stream().filter(seminar -> seminar.id() == id).findFirst();
    }

    @Override
    public Seminar create(Seminar seminar) {
        seminarList.add(seminar);
        return seminar;
    }

    @Override
    public List<Seminar> findAllByStartDateAfter(final LocalDate startDate) {
        return seminarList.stream()
                .filter(seminar -> seminar.startDate().isAfter(startDate))
                .toList();
    }

	   @Override
	   public List<Seminar> listAll() {
			  return seminarList;
	   }

	   @Override
	   public void deleteById(UUID id) {
			  Optional<Seminar> seminar = this.findById(id);
			  if (seminar.isPresent()) {
					 seminarList.remove(seminar.get());
			  }
	   }
}