package com.liksi.hexagonal.seminar.business;

import com.liksi.hexagonal.seminar.business.exception.InvalidRequestException;
import com.liksi.hexagonal.seminar.model.Seminar;
import com.liksi.hexagonal.seminar.ports.persistence.SeminarRepository;

import java.util.Optional;
import java.util.UUID;

public class SeminarService {

	   private SeminarRepository seminarRepository;

	   public SeminarService(SeminarRepository seminarRepository) {
			  this.seminarRepository = seminarRepository;
	   }

	   public Seminar create(Seminar seminar) {
			  if(seminarRepository.findById(seminar.id()).isPresent()) {
					 throw new InvalidRequestException("Seminar " + seminar.id() + " already exists");
			  }
			  return seminarRepository.create(seminar);
	   }

	   public Optional<Seminar> getById(UUID id) {
			  return seminarRepository.findById(id);
	   }

}
