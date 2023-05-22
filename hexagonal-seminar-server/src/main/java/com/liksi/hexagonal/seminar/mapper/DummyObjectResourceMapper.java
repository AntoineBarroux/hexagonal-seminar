package com.liksi.hexagonal.seminar.mapper;

import com.liksi.hexagonal.seminar.model.DummyObject;
import com.liksi.hexagonal.seminar.resource.DummyObjectResource;
import org.mapstruct.Mapper;

@Mapper
public interface DummyObjectResourceMapper {

    DummyObject toModel(DummyObjectResource resource);
    DummyObjectResource toResource(DummyObject model);
}
