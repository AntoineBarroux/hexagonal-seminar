package com.liksi.hexagonal.seminar.http;

import com.liksi.hexagonal.seminar.business.SeminarService;
import com.liksi.hexagonal.seminar.business.exception.InvalidRequestException;
import com.liksi.hexagonal.seminar.business.exception.NotFoundException;
import com.liksi.hexagonal.seminar.mapper.SeminarResourceMapper;
import com.liksi.hexagonal.seminar.resource.SeminarResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/seminar")
public class SeminarController {
	   private static final Logger logger = LoggerFactory.getLogger(SeminarController.class);
	   private final SeminarResourceMapper seminarResourceMapper;

	   private final SeminarService seminarService;


	   public SeminarController(final SeminarResourceMapper seminarResourceMapper, final SeminarService seminarService) {
			  this.seminarResourceMapper = seminarResourceMapper;
			  this.seminarService = seminarService;
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

}
