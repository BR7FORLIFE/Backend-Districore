package com.tecno_comfenalco.pa.infrastructure.store.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.tecno_comfenalco.pa.application.store.ports.IStoreRepositoryPort;
import com.tecno_comfenalco.pa.domain.store.models.StoreModel;
import com.tecno_comfenalco.pa.infrastructure.store.entity.StoreDocument;
import com.tecno_comfenalco.pa.infrastructure.store.mapper.StoreMapper;
import com.tecno_comfenalco.pa.infrastructure.store.repository.mongo.StoreRepository;
import com.tecno_comfenalco.pa.shared.utils.http.PagedResult;
import com.tecno_comfenalco.pa.shared.utils.http.PaginationMeta;

@Repository
public class StoreRepositoryAdapter implements IStoreRepositoryPort {

    private final StoreRepository storeRepository;

    public StoreRepositoryAdapter(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    public boolean existsStoreByNit(String nit) {
        return storeRepository.existsByNit(nit);
    }

    @Override
    public StoreModel save(StoreModel storeModel) {
        StoreDocument storeDocument = StoreMapper.toEntity(storeModel);
        StoreDocument saved = storeRepository.save(storeDocument);

        return StoreMapper.toDomain(saved);
    }

    @Override
    public PagedResult<StoreModel> findAllpaged(String name, Integer page, Integer size, String sortBy,
            String direction) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        Page<StoreDocument> result;

        if (name != null && !name.isBlank()) {
            result = storeRepository.findByNameContainingIgnoreCase(name, pageable);
        } else {
            result = storeRepository.findAll(pageable);
        }

        List<StoreModel> models = result.getContent()
                .stream()
                .map(StoreMapper::toDomain)
                .collect(Collectors.toList());

        PaginationMeta meta = new PaginationMeta(result.getNumber(), result.getSize(), result.getTotalElements(),
                result.getTotalPages(), result.hasNext());

        return new PagedResult<StoreModel>(models, meta);
    }

    @Override
    public Optional<StoreModel> findById(UUID id) {
        return storeRepository.findById(id)
                .map(StoreMapper::toDomain);
    }

    @Override
    public boolean existsStoreById(UUID id) {
        return storeRepository.existsById(id);
    }

    @Override
    public void deleteStoreById(UUID id) {
        storeRepository.deleteById(id);
    }

    @Override
    public Optional<StoreModel> findByUserId(UUID userId) {
        return storeRepository.findByUserId(userId)
                .map(StoreMapper::toDomain);
    }

    @Override
    public PagedResult<StoreModel> findByIdIn(Collection<UUID> ids, String name, Integer page, Integer size,
            String sortBy, String direction) {

        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        Page<StoreDocument> result;

        if (name != null && !name.isBlank()) {
            result = storeRepository.findByIdInAndNameContainingIgnoreCase(ids, name, pageable);
        } else {
            result = storeRepository.findByIdIn(ids, pageable);
        }

        List<StoreModel> models = result.getContent()
                .stream()
                .map(StoreMapper::toDomain)
                .collect(Collectors.toList());

        PaginationMeta meta = new PaginationMeta(
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages(),
                result.hasNext());

        return new PagedResult<StoreModel>(models, meta);
    }
}
