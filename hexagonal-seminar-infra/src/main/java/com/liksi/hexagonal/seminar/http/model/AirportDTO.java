package com.liksi.hexagonal.seminar.http.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AirportDTO(
		String name,
		@JsonProperty("iata_code")
		String iataCode,
		@JsonProperty("icao_code")
		String icaoCode,
		Double lat,
		Double lng,
		@JsonProperty("country_code")
		String countryCode
) {
}
