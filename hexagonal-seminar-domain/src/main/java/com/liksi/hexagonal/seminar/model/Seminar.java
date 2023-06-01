package com.liksi.hexagonal.seminar.model;

import java.time.LocalDate;
import java.util.UUID;

public record Seminar(
		UUID id,
		Airport departure,
		Airport arrival,
		LocalDate startDate,
		int attendees,
		long carbon
) {

	public Seminar(Airport departureAirport, Airport arrivalAirport, RouteConsommation routeConsommation, int attendees) {
		this(UUID.randomUUID(), departureAirport, arrivalAirport, LocalDate.now(), attendees, routeConsommation.consommation());
	}
}
