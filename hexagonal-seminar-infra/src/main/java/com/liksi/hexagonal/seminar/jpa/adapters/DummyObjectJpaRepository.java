package com.liksi.hexagonal.seminar.jpa.adapters;

import com.liksi.hexagonal.seminar.jpa.model.DummyObjectEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DummyObjectJpaRepository extends PagingAndSortingRepository<DummyObjectEntity, UUID> {

    Optional<DummyObjectEntity> findById(UUID id);
}
