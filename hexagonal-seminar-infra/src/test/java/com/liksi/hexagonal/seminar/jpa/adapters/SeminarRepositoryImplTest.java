package com.liksi.hexagonal.seminar.jpa.adapters;

import com.liksi.hexagonal.seminar.AbstractIntegrationTest;
import com.liksi.hexagonal.seminar.model.Airport;
import com.liksi.hexagonal.seminar.model.Seminar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class SeminarRepositoryImplTest extends AbstractIntegrationTest {

    @Autowired
    private SeminarRepositoryImpl seminarRepository;

    @Autowired
    private SeminarJpaRepository seminarJpaRepository;

    @BeforeEach
    void setup() {
        seminarJpaRepository.deleteAll();
    }

    @Test
    public void insertAndFetchSeminar() {
        final UUID seminarId = UUID.randomUUID();
        Seminar seminar = new Seminar(seminarId, new Airport("CDG", "FR"), new Airport("RNS", "FR"), LocalDate.now(), 50, 1000);
        Seminar created = seminarRepository.create(seminar);
        assertThat(created).isEqualTo(seminar);

        Optional<Seminar> found = seminarRepository.findById(seminarId);
        assertThat(found.get()).isEqualTo(created);
    }

    @Test
    void should_correctly_get_seminars_after_a_given_date() {
        // Given
        final UUID seminarId = UUID.randomUUID();
        Seminar oneDayAfter = new Seminar(seminarId, new Airport("CDG", "FR"), new Airport("RNS", "FR"), LocalDate.now().plusDays(1), 50, 1000);
        Seminar oneDayBefore = new Seminar(UUID.randomUUID(), new Airport("CDG", "FR"), new Airport("RNS", "FR"), LocalDate.now().minusDays(1), 50, 1000);
        Seminar oneDayAfterCreated = seminarRepository.create(oneDayAfter);
        seminarRepository.create(oneDayBefore);

        // When
        final var seminars = seminarRepository.findAllByStartDateAfter(LocalDate.now());

        // Then
        assertThat(seminars).containsExactly(oneDayAfterCreated);
    }

	   @Test
	   public void listAll() {
			  final UUID seminarId = UUID.randomUUID();
			  Seminar seminar = new Seminar(
					  seminarId,
					  new Airport("CDG", "FR"),
					  new Airport("RNS", "FR"),
					  LocalDate.now(),
					  50,
					  1000
			  );
			  seminarRepository.create(seminar);
			  assertThat(seminarRepository.listAll()).hasSize(1);
	   }

	   @Test
	   public void delete() {
			  final UUID seminarId = UUID.randomUUID();
			  Seminar seminar = new Seminar(
					  seminarId,
					  new Airport("CDG", "FR"),
					  new Airport("RNS", "FR"),
					  LocalDate.now(),
					  50,
					  1000
			  );
			  seminarRepository.create(seminar);
			  assertThat(seminarRepository.listAll()).hasSize(1);
			  seminarRepository.deleteById(seminarId);
			  assertThat(seminarRepository.listAll()).hasSize(0);
	   }

	   @Test
	   public void delete_a_missing_entry_is_ok() {
			  final UUID seminarId = UUID.randomUUID();
			  seminarRepository.deleteById(seminarId);
	   }


}