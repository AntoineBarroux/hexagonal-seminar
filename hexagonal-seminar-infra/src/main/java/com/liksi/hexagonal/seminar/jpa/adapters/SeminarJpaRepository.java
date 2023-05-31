package com.liksi.hexagonal.seminar.jpa.adapters;

import com.liksi.hexagonal.seminar.jpa.entity.SeminarEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SeminarJpaRepository extends PagingAndSortingRepository<SeminarEntity, UUID>, CrudRepository<SeminarEntity, UUID> {

    Optional<SeminarEntity> findById(UUID id);
}
