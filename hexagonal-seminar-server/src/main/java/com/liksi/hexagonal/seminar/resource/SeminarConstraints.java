package com.liksi.hexagonal.seminar.resource;

public record SeminarConstraints(
		String departure,
		Long maxCarbonConsumption,
		int attendees
) {
}
