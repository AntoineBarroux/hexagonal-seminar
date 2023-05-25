package com.liksi.hexagonal.seminar.jpa.mapper;

import com.liksi.hexagonal.seminar.model.DummyObject;
import com.liksi.hexagonal.seminar.jpa.model.DummyObjectEntity;
import org.mapstruct.Mapper;

@Mapper
public interface DummyObjectMapper {

    DummyObjectEntity toEntity(DummyObject dummyObject);
    DummyObject toModel(DummyObjectEntity dummyObjectEntity);
}
