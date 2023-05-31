package com.liksi.hexagonal.seminar.http;

import com.liksi.hexagonal.seminar.mapper.DummyObjectResourceMapper;
import com.liksi.hexagonal.seminar.resource.DummyObjectResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController("/api/dummy")
public class DummyObjectController {

    private final DummyObjectResourceMapper dummyObjectResourceMapper;

    public DummyObjectController(
            final DummyObjectResourceMapper dummyObjectResourceMapper
    ) {
        this.dummyObjectResourceMapper = dummyObjectResourceMapper;
    }

    @GetMapping("/{id}")
    public DummyObjectResource getDummyObject(@PathVariable UUID id) {
        return null;
    }
}
