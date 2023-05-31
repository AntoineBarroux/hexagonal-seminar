package com.liksi.hexagonal.seminar.business;

import com.liksi.hexagonal.seminar.business.exception.InvalidRequestException;
import com.liksi.hexagonal.seminar.model.Airport;
import com.liksi.hexagonal.seminar.model.Seminar;
import com.liksi.hexagonal.seminar.ports.persistence.FakeSeminarRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class SeminarServiceTest {

	   private SeminarService seminarService = new SeminarService(new FakeSeminarRepository());

	   @Test
	   public void createASeminarAndFetchIt() {
			  final UUID seminarId = UUID.randomUUID();
			  Seminar seminar = new Seminar(
					  seminarId,
					  new Airport("CDG", "FR"),
					  new Airport("RNS", "FR"),
					  LocalDate.now(),
					  50,
					  1000
			  );
			  Seminar created = seminarService.create(seminar);
			  assertThat(created).isEqualTo(seminar);

			  Optional<Seminar> read = seminarService.getById(seminarId);
			  assertThat(read.get()).isEqualTo(seminar);
	   }

	   @Test
	   public void createASeminarTwice() {
			  final UUID seminarId = UUID.randomUUID();
			  Seminar seminar = new Seminar(
					  seminarId,
					  new Airport("CDG", "FR"),
					  new Airport("RNS", "FR"),
					  LocalDate.now(),
					  50,
					  1000
			  );
			  Seminar created = seminarService.create(seminar);
			  assertThat(created).isEqualTo(seminar);

			  assertThatThrownBy(() -> seminarService.create(seminar))
					  .hasMessage("Seminar " + seminarId + " already exists")
					  .isInstanceOf(InvalidRequestException.class);
	   }

}