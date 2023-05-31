package com.liksi.hexagonal.seminar.jpa.adapters;

import com.liksi.hexagonal.seminar.AbstractIntegrationTest;
import com.liksi.hexagonal.seminar.model.Airport;
import com.liksi.hexagonal.seminar.model.Seminar;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class SeminarRepositoryImplTest extends AbstractIntegrationTest {

	   @Autowired
	   private SeminarRepositoryImpl seminarRepository;

	   @Test
	   public void insertAndFetchSeminar() {
			  final UUID seminarId = UUID.randomUUID();
			  Seminar seminar = new Seminar(
					  seminarId,
					  new Airport("CDG", "FR"),
					  new Airport("RNS", "FR"),
					  LocalDate.now(),
					  50,
					  1000
			  );
			  Seminar created = seminarRepository.create(seminar);
			  assertThat(created).isEqualTo(seminar);

			  Optional<Seminar> found = seminarRepository.findById(seminarId);
			  assertThat(found.get()).isEqualTo(created);
	   }

}