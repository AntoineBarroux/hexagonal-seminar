package com.liksi.hexagonal.seminar.http;

import com.liksi.hexagonal.seminar.business.DummyObjectCrudOperationsService;
import com.liksi.hexagonal.seminar.mapper.DummyObjectResourceMapper;
import com.liksi.hexagonal.seminar.resource.DummyObjectResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController("/api/dummy")
public class DummyObjectController {

    private final DummyObjectCrudOperationsService dummyObjectService;
    private final DummyObjectResourceMapper dummyObjectResourceMapper;

    public DummyObjectController(
            final DummyObjectCrudOperationsService dummyObjectService,
            final DummyObjectResourceMapper dummyObjectResourceMapper
    ) {
        this.dummyObjectService = dummyObjectService;
        this.dummyObjectResourceMapper = dummyObjectResourceMapper;
    }

    @GetMapping("/{id}")
    public DummyObjectResource getDummyObject(@PathVariable UUID id) {
        return dummyObjectResourceMapper.toResource(dummyObjectService.get(id));
    }
}
