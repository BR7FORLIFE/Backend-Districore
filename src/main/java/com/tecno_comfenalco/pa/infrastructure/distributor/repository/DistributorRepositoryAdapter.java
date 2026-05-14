package com.tecno_comfenalco.pa.infrastructure.distributor.repository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.tecno_comfenalco.pa.application.distributor.ports.IDistributorRepositoryPort;
import com.tecno_comfenalco.pa.domain.distributor.model.DistributorModel;
import com.tecno_comfenalco.pa.infrastructure.distributor.entity.DistributorDocument;
import com.tecno_comfenalco.pa.infrastructure.distributor.mapper.DistributorMapper;
import com.tecno_comfenalco.pa.infrastructure.distributor.repository.mongo.DistributorRepository;
import com.tecno_comfenalco.pa.shared.utils.http.PagedResult;
import com.tecno_comfenalco.pa.shared.utils.http.PaginationMeta;

@Repository
public class DistributorRepositoryAdapter implements IDistributorRepositoryPort {

    private final DistributorRepository distributorRepository;

    public DistributorRepositoryAdapter(DistributorRepository distributorRepository) {
        this.distributorRepository = distributorRepository;
    }

    @Override
    public boolean existsDistributorByEmail(String email) {
        return distributorRepository.existsByEmail(email);
    }

    @Override
    public boolean existsDistributorByNit(String nit) {
        return distributorRepository.existsByNit(nit);
    }

    @Override
    public DistributorModel save(DistributorModel distributorModel) {
        DistributorDocument distributorDocument = DistributorMapper.toEntity(distributorModel);
        DistributorDocument saved = distributorRepository.save(distributorDocument);

        return DistributorMapper.toDomain(saved);
    }

    @Override
    public Optional<DistributorModel> findById(UUID id) {
        return distributorRepository.findById(id)
                .map(DistributorMapper::toDomain);
    }

    @Override
    public Optional<DistributorModel> findByNIT(String NIT) {
        return distributorRepository.findByNit(NIT)
                .map(DistributorMapper::toDomain);
    }

    @Override
    public Optional<DistributorModel> findByUserId(UUID userId) {
        return distributorRepository.findByUserId(userId)
                .map(DistributorMapper::toDomain);
    }

    @Override
    public boolean existsDistributorById(UUID distributorId) {
        return distributorRepository.existsById(distributorId);
    }

    @Override
    public PagedResult<DistributorModel> findAllPaged(String name, Integer page, Integer size, String sortBy,
            String direction) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        Page<DistributorDocument> result;

        if (name != null && !name.isBlank()) {
            result = distributorRepository.findByNameContainingIgnoreCase(name, pageable);

        } else {
            result = distributorRepository.findAll(pageable);
        }

        List<DistributorModel> models = result.getContent()
                .stream()
                .map(DistributorMapper::toDomain)
                .collect(Collectors.toList());

        PaginationMeta meta = new PaginationMeta(result.getNumber(), result.getSize(), result.getTotalElements(),
                result.getTotalPages(), result.hasNext());

        return new PagedResult<DistributorModel>(models, meta);
    }

    @Override
    public PagedResult<DistributorModel> findByIdIn(Collection<UUID> ids, String name, Integer page, Integer size,
            String sortBy, String direction) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        Page<DistributorDocument> result;

        if (name != null && !name.isBlank()) {
            result = distributorRepository.findByIdInAndNameContainingIgnoreCase(ids, name, pageable);
        } else {
            result = distributorRepository.findByIdIn(ids, pageable);
        }

        List<DistributorModel> models = result.getContent()
                .stream()
                .map(DistributorMapper::toDomain)
                .collect(Collectors.toList());

        PaginationMeta meta = new PaginationMeta(
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages(),
                result.hasNext());

        return new PagedResult<DistributorModel>(models, meta);
    }

    @Override
    public boolean existsDistributorByUserId(UUID userDistributorId) {
        
        return false;
    }
}
