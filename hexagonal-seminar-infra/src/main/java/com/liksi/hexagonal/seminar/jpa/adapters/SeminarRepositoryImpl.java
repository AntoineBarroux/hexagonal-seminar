package com.liksi.hexagonal.seminar.jpa.adapters;

import com.liksi.hexagonal.seminar.jpa.mapper.SeminarMapper;
import com.liksi.hexagonal.seminar.model.Seminar;
import com.liksi.hexagonal.seminar.ports.persistence.SeminarRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class SeminarRepositoryImpl implements SeminarRepository {

    private final SeminarJpaRepository seminarJpaRepository;
    private final SeminarMapper seminarMapper;

    public SeminarRepositoryImpl(final SeminarJpaRepository seminarJpaRepository, final SeminarMapper seminarMapper) {
        this.seminarJpaRepository = seminarJpaRepository;
        this.seminarMapper = seminarMapper;
    }

    @Override
    public Optional<Seminar> findById(UUID id) {
        return seminarJpaRepository.findById(id).map(seminarMapper::toModel);
    }

    @Override
    public Seminar create(Seminar seminar) {
        return seminarMapper.toModel(seminarJpaRepository.save(seminarMapper.toEntity(seminar)));
    }

    @Override
    public List<Seminar> findAllByStartDateAfter(final LocalDate startDate) {
        return seminarJpaRepository.findAllByStartDateAfter(startDate).stream().map(seminarMapper::toModel).toList();
    }

    @Override
    public List<Seminar> listAll() {
        List<Seminar> result = new ArrayList<>();
        seminarJpaRepository.findAll().forEach(seminarEntity -> result.add(seminarMapper.toModel(seminarEntity)));
        return result;
    }

    @Override
    public void deleteById(UUID id) {
        seminarJpaRepository.deleteById(id);
    }
}
