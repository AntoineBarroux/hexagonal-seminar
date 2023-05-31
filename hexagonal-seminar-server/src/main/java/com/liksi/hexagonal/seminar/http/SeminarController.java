package com.liksi.hexagonal.seminar.http;

import com.liksi.hexagonal.seminar.business.SeminarFinderService;
import com.liksi.hexagonal.seminar.business.SeminarService;
import com.liksi.hexagonal.seminar.business.exception.InvalidRequestException;
import com.liksi.hexagonal.seminar.business.exception.NotFoundException;
import com.liksi.hexagonal.seminar.mapper.SeminarResourceMapper;
import com.liksi.hexagonal.seminar.resource.SeminarConstraints;
import com.liksi.hexagonal.seminar.resource.SeminarResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/seminar")
public class SeminarController {
	   private static final Logger logger = LoggerFactory.getLogger(SeminarController.class);
	   private final SeminarResourceMapper seminarResourceMapper;

	   private final SeminarService seminarService;

	   private final SeminarFinderService seminarFinderService;


	   public SeminarController(final SeminarResourceMapper seminarResourceMapper, final SeminarService seminarService, SeminarFinderService seminarFinderService) {
			  this.seminarResourceMapper = seminarResourceMapper;
			  this.seminarService = seminarService;
			  this.seminarFinderService = seminarFinderService;
	   }

	   @GetMapping("/{id}")
	   public SeminarResource getSeminar(@PathVariable UUID id) {
			  logger.info("Fetching seminar {}", id);
			  return seminarResourceMapper.toResource(seminarService.getById(id).orElseThrow(() -> new NotFoundException("Seminar " + id + " does not exist")));
	   }

	   @PutMapping("/{id}")
	   public SeminarResource createSeminar(@PathVariable UUID id, @RequestBody SeminarResource seminarResource) {
			  if (!seminarResource.id().equals(id)) {
					 throw new InvalidRequestException("id in the request path should be the same as in the payload");
			  }
			  logger.info("Putting seminar {}", id);
			  return seminarResourceMapper.toResource(seminarService.create(seminarResourceMapper.toModel(seminarResource)));
	   }

	   @GetMapping
	   public List<SeminarResource> listSeminars() {
			  logger.info("Listing seminars");
			  return seminarService.listAll().stream()
					  .map(seminarResourceMapper::toResource)
					  .collect(Collectors.toList());
	   }

	   @PostMapping("/suggest")
	   public SeminarResource findBestRoute(@RequestBody SeminarConstraints seminarConstraints) {
			  logger.info("Searching best match for {}", seminarConstraints);
			  var suggestion = seminarFinderService.findSeminarDestinationFrom(seminarConstraints.departure(), seminarConstraints.attendees(), seminarConstraints.maxCarbonConsumption()).orElseThrow(() -> new NotFoundException("no suitable seminar"));
			  return seminarResourceMapper.toResource(suggestion);
	   }

	   @DeleteMapping("/{id}")
	   @ResponseStatus(HttpStatus.NO_CONTENT)
	   public void deleteSeminar(@PathVariable UUID id) {
			  logger.info("Deleting seminar {}", id);
			  seminarService.deleteById(id);
	   }
}
