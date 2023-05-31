package com.liksi.hexagonal.seminar.http.mapper;

import com.liksi.hexagonal.seminar.http.model.RouteDTO;
import com.liksi.hexagonal.seminar.model.Route;
import org.mapstruct.Mapper;

@Mapper
public interface RouteMapper {
    Route toModel(RouteDTO routeDTO);
}
