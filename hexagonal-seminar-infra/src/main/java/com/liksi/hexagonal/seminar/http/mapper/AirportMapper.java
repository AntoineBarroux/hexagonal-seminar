package com.liksi.hexagonal.seminar.http.mapper;

import com.liksi.hexagonal.seminar.http.model.AirportDTO;
import com.liksi.hexagonal.seminar.model.Airport;
import org.mapstruct.Mapper;

@Mapper
public interface AirportMapper {
    Airport toModel(AirportDTO airportDTO);
}
