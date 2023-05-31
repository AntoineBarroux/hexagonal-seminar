package com.liksi.hexagonal.seminar.jpa.mapper;

import com.liksi.hexagonal.seminar.jpa.entity.SeminarEntity;
import com.liksi.hexagonal.seminar.model.Seminar;
import org.mapstruct.Mapper;

@Mapper
public interface SeminarMapper {

    SeminarEntity toEntity(Seminar seminar);
    Seminar toModel(SeminarEntity seminarEntity);

}
