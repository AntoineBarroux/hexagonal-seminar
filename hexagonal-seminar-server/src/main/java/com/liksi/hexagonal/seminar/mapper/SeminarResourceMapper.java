package com.liksi.hexagonal.seminar.mapper;

import com.liksi.hexagonal.seminar.model.Seminar;
import com.liksi.hexagonal.seminar.resource.SeminarResource;
import org.mapstruct.Mapper;

@Mapper
public interface SeminarResourceMapper {

	   SeminarResource toResource(Seminar seminar);

	   Seminar toModel(SeminarResource seminarResource);

}
