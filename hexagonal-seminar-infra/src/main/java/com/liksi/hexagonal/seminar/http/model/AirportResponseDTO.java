package com.liksi.hexagonal.seminar.http.model;

import java.util.List;

public record AirportResponseDTO(
        List<AirportDTO> response
) {
}
