package com.liksi.hexagonal.seminar.resource;


import java.time.LocalDate;
import java.util.UUID;

public record SeminarResource(
		UUID id,
		AirportResource departure,
		AirportResource arrival,
		LocalDate startDate,
		int attendees,
		int carbon
) { }