package com.liksi.hexagonal.seminar.http;

import com.liksi.hexagonal.seminar.IntegrationTestConfiguration;
import com.liksi.hexagonal.seminar.business.SeminarFinderService;
import com.liksi.hexagonal.seminar.business.SeminarService;
import com.liksi.hexagonal.seminar.mapper.SeminarResourceMapper;
import com.liksi.hexagonal.seminar.model.Airport;
import com.liksi.hexagonal.seminar.model.Seminar;
import com.liksi.hexagonal.seminar.resource.AirportResource;
import com.liksi.hexagonal.seminar.resource.SeminarConstraints;
import com.liksi.hexagonal.seminar.resource.SeminarResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ProblemDetail;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.net.URI;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {IntegrationTestConfiguration.class})
@WebFluxTest(controllers = {SeminarController.class, ControllerExceptionHandler.class})
class SeminarControllerTest {
	   @Autowired
	   private WebTestClient webTestClient;
	   @Autowired
	   private SeminarResourceMapper seminarResourceMapper;
	   @MockBean
	   private SeminarService seminarService;
	   @MockBean
	   private SeminarFinderService seminarFinderService;

	   @Test
	   public void fetchObjectNotFound() {
			  webTestClient
					  .get()
					  .exchange()
					  .expectStatus().isNotFound();
	   }

	   @Test
	   public void fetchExistingSeminar() {
			  UUID id = UUID.fromString("dc280ecf-46e1-4094-9774-73049339033a");
			  LocalDate now = LocalDate.now();
			  when(seminarService.getById(id)).thenReturn(Optional.of(getSeminar(id, now)));

			  SeminarResource resource = webTestClient
					  .get().uri(URI.create("/api/seminar/dc280ecf-46e1-4094-9774-73049339033a"))
					  .exchange()
					  .expectStatus().isOk()
					  .returnResult(SeminarResource.class).getResponseBody().blockFirst();
			  assertThat(resource).isEqualTo(getSeminarResource(id, now));

	   }

	   @Test
	   public void createSeminar() {
			  UUID id = UUID.fromString("dc280ecf-46e1-4094-9774-73049339033a");
			  LocalDate now = LocalDate.now();
			  Seminar seminar = getSeminar(id, now);
			  when(seminarService.create(seminar)).thenReturn(seminar);

			  SeminarResource resource = webTestClient
					  .put().uri(URI.create("/api/seminar/dc280ecf-46e1-4094-9774-73049339033a"))
					  .bodyValue(getSeminarResource(id, now))
					  .exchange()
					  .expectStatus().isOk()
					  .returnResult(SeminarResource.class).getResponseBody().blockFirst();
			  assertThat(resource).isEqualTo(getSeminarResource(id, now));

	   }

	   @Test
	   public void createSeminarWithWrongId() {
			  UUID id = UUID.fromString("dc280ecf-46e1-4094-9774-730493390BAD");
			  LocalDate now = LocalDate.now();
			  Seminar seminar = getSeminar(id, now);
			  when(seminarService.create(seminar)).thenReturn(seminar);

			  webTestClient
					  .put().uri(URI.create("/api/seminar/dc280ecf-46e1-4094-9774-73049339033a"))
					  .bodyValue(getSeminarResource(id, now))
					  .exchange()
					  .expectStatus().isBadRequest();

	   }

	   @Test
	   public void listSeminars() {
			  UUID id = UUID.fromString("dc280ecf-46e1-4094-9774-730493390BAD");
			  LocalDate now = LocalDate.now();
			  Seminar seminar = getSeminar(id, now);
			  when(seminarService.listAll()).thenReturn(Collections.singletonList(seminar));

			  webTestClient
					  .get().uri("/api/seminar")
					  .exchange()
					  .expectStatus().isOk()
					  .expectBodyList(SeminarResource.class)
					  .hasSize(1);
	   }

	   @Test
	   public void suggestSeminar() {
			  UUID id = UUID.fromString("dc280ecf-46e1-4094-9774-730493390BAD");
			  LocalDate now = LocalDate.now();
			  Seminar seminar = getSeminar(id, now);
			  when(seminarFinderService.findSeminarDestinationFrom(anyString(), anyInt(), anyLong())).thenReturn(Optional.of(seminar));

			  SeminarResource seminarResource =webTestClient
					  .post().uri("/api/seminar/suggest")
					  .bodyValue(new SeminarConstraints("RNS", 1000L, 5))
					  .exchange()
					  .expectStatus().isOk()
					  .returnResult(SeminarResource.class).getResponseBody().blockFirst();

			  assertThat(seminarResource.id()).isEqualTo(id);
	   }

	   @Test
	   public void deleteSeminar() {
			  UUID id = UUID.fromString("dc280ecf-46e1-4094-9774-730493390");
			  LocalDate now = LocalDate.now();
			  Seminar seminar = getSeminar(id, now);
			  doNothing().when(seminarService).deleteById(any());

			 webTestClient
					  .delete().uri("/api/seminar/dc280ecf-46e1-4094-9774-730493390")
					  .exchange()
					  .expectStatus().isNoContent();

			  verify(seminarService).deleteById(id);
	   }

	@Test
	public void suggestSeminarNotFound() {
		when(seminarFinderService.findSeminarDestinationFrom(anyString(), anyInt(), anyLong())).thenReturn(Optional.empty());

		ProblemDetail problemDetail = webTestClient
				.post().uri("/api/seminar/suggest")
				.bodyValue(new SeminarConstraints("RNS", 1000L, 5))
				.exchange()
				.expectStatus().isNotFound()
				.returnResult(ProblemDetail.class).getResponseBody().blockFirst();

		assertThat(problemDetail.getTitle()).isEqualTo("No seminar was found according to your constraints");
	}

	   private SeminarResource getSeminarResource(UUID uuid, LocalDate now) {
			  return new SeminarResource(
					  uuid,
					  new AirportResource("CDG", "FR"),
					  new AirportResource("RNS", "FR"),
					  now,
					  50,
					  1000
			  );
	   }

	   private Seminar getSeminar(UUID uuid, LocalDate now) {
			  return new Seminar(
					  uuid,
					  new Airport("CDG", "FR"),
					  new Airport("RNS", "FR"),
					  now,
					  50,
					  1000
			  );
	   }
}