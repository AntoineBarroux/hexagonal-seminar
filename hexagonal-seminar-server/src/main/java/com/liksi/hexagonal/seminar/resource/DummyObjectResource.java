package com.liksi.hexagonal.seminar.resource;

import java.util.UUID;

public record DummyObjectResource (
        UUID id,
        String name
) { }