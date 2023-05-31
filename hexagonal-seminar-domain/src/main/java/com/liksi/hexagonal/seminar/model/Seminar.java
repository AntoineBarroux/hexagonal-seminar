package com.liksi.hexagonal.seminar.model;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public record Seminar(
		UUID id,
		Airport departure,
		Airport arrival,
		LocalDate startDate,
		int attendees,
		int carbon
) {
}
